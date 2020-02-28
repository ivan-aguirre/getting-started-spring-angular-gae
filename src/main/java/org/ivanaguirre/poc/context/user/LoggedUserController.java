package org.ivanaguirre.poc.context.user;

import org.ivanaguirre.poc.PocApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class LoggedUserController {

    private static final Logger log = LoggerFactory.getLogger(PocApplication.class);

    /**
     * It’s not a great idea to return a whole OAuth2User in an endpoint since it might contain information
     * you would rather not reveal to a browser client.
     */
    @GetMapping("/loggeduser")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        log.info("principal =>" + principal.getClass().getName());
        log.info("principal =>" + principal.getAttributes());
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
}
