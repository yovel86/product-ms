package com.projects.product_ms.components;

import com.projects.product_ms.dtos.user.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthUtils {

    private final RestTemplate restTemplate;
//    private final String BASE_URL = "http://ecom-user-service-dev.ap-south-1.elasticbeanstalk.com/users";
    private final String BASE_URL = "http://localhost:8080/users";

    @Autowired
    public AuthUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean validateToken(String tokenValue) {
        JSONObject body = new JSONObject();
        try {
            body.put("tokenValue", tokenValue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        try {
            ResponseEntity<Token> response = this.restTemplate.exchange(BASE_URL + "/validate-token", HttpMethod.POST, entity, Token.class);
            return response.getStatusCode().equals(HttpStatus.OK);
        } catch (Exception e) {
            return false;
        }
    }

}
