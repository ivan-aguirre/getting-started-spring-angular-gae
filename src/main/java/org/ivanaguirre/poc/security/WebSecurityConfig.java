package org.ivanaguirre.poc.security;

import org.ivanaguirre.poc.context.user.*;
import org.ivanaguirre.poc.context.user.entity.IdentityProvider;
import org.ivanaguirre.poc.context.user.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configurers/oauth2/client/OAuth2LoginConfigurer.UserInfoEndpointConfig.html
        http
                .authorizeRequests(a -> a
                        //.antMatchers("/employees/do").hasAuthority("employee_viewer")
                        .antMatchers("/oauth2/authorization/google").permitAll()
                        .anyRequest().authenticated()
                )

                .logout(l -> l.logoutSuccessUrl("/").permitAll())

                .csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )

                .oauth2Login()
                .userInfoEndpoint()
                .oidcUserService(oidcUserService());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() { //TODO: can we change this corsConfigurer name??
        return new WebMvcConfigurer() {
            /*
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
             */

            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
            }
        };
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();
        return request -> {

            Date now = new Date();

            OidcUser user = delegate.loadUser(request);

            if (log.isDebugEnabled()) {
                log.debug("user =>" + user.getClass().getName());
                log.debug(user.getAttributes().toString());

                log.debug("client registration =>");
                log.debug(request.getClientRegistration().toString());
            }

            String email = user.<String>getAttribute("email");
            if (email == null || !email.startsWith("ivan")) {
                throw new OAuth2AuthenticationException(new OAuth2Error("There's no e-mail on the account"));
                //TODO: handle this error page
            }

            String name = user.<String>getAttribute("name");
            UserData data = userService.newOrExistent(email, name, now, IdentityProvider.GOOGLE);

            Set<GrantedAuthority> mappedAuthorities = mapToGrantedAuthorities(data);

            return new DefaultOidcUser(mappedAuthorities, user.getIdToken(), user.getUserInfo());
        };
    }

    private Set<GrantedAuthority> mapToGrantedAuthorities(UserData data) {
        return data.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
