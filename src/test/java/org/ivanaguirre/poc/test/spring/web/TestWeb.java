package org.ivanaguirre.poc.test.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestWeb {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @org.junit.jupiter.api.Test
    public void test() {
        String result = this.restTemplate.getForObject("http://localhost:" + port + "/api/users",
                String.class);
        assertThat(result).isNotBlank();
        assertThat(result).isNotEqualTo("[]");
    }
}
