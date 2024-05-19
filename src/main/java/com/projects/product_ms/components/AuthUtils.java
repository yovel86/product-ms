package com.projects.product_ms.components;

import com.projects.product_ms.dtos.user.Token;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthUtils {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8080/users";

    public AuthUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean validateToken(String tokenValue) throws JSONException {
        JSONObject body = new JSONObject();
        body.put("tokenValue", tokenValue);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        try {
            Token token = this.restTemplate.postForObject(BASE_URL + "/validate-token" , entity, Token.class);
            return token != null;
        } catch (Exception e) {
            return false;
        }
    }

}
