package com.smen.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smen.DTO.KeycloakLoginDTO;
import com.smen.DTO.KeycloakTokenDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@NoArgsConstructor
@RequestMapping("/api/keycloak")
@CrossOrigin("*")
public class KeycloakController {

//    @PreAuthorize("hasRole('client_user')")


    @Value("${app.api.keycloak_url}")
    private String KEYCLOAK_URL;

    @Value("${app.api.keycloak_client}")
    private String KEYCLOAK_CLIENT;

    @PostMapping(path="/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody KeycloakLoginDTO keycloakLogin, HttpServletResponse res) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = KEYCLOAK_URL + "/realms/smen/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "grant_type=password&client_id="+KEYCLOAK_CLIENT+"&username="
                + keycloakLogin.getUsername()
                + "&password="+keycloakLogin.getPassword();

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        System.out.println(response.getBody());
        ObjectMapper objectMapper = new ObjectMapper();

        KeycloakTokenDTO token = objectMapper.readValue(response.getBody(), KeycloakTokenDTO.class);

        Cookie cookie = new Cookie("token", token.getAccess_token());


        cookie.setSecure(true);
        cookie.setHttpOnly(false);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        res.addCookie(cookie);

        return ResponseEntity.ok(true);
    }


    @PostMapping(path="/register")
    public ResponseEntity<Boolean> registerUser(@RequestBody KeycloakLoginDTO keycloakRegister, HttpServletResponse res) throws IOException {
        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate accessTokenTemplate = new RestTemplate();
        String accessTokenUrl = KEYCLOAK_URL + "/realms/smen/protocol/openid-connect/token";
        String accessTokenBody = "grant_type=client_credentials&client_id="+KEYCLOAK_CLIENT+"&client_secret=URVyOvbZ4S0gwxHHKogLql2PY6bBqB0T";

        HttpEntity<String> accessTokenEntity = new HttpEntity<>(accessTokenBody, accessTokenHeaders);
        ResponseEntity<String> accessTokenResponse = accessTokenTemplate.postForEntity(accessTokenUrl, accessTokenEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        KeycloakTokenDTO accessToken = objectMapper.readValue(accessTokenResponse.getBody(), KeycloakTokenDTO.class);

        HttpHeaders registrationHeaders = new HttpHeaders();

        registrationHeaders.setContentType(MediaType.APPLICATION_JSON);
        registrationHeaders.setBearerAuth(accessToken.getAccess_token());

        RestTemplate restTemplate = new RestTemplate();
        String url = KEYCLOAK_URL + "/admin/realms/smen/users";
        Map<String, Object> registrationMap = new HashMap<>();
        registrationMap.put("username", keycloakRegister.getUsername());
        registrationMap.put("enabled", true);
        registrationMap.put("firstName", "Filip");
        registrationMap.put("lastName", "Grabovac");
        registrationMap.put("email", "fake@gmail.com");
registrationMap.put("emailVerified", false);

        registrationMap.put("credentials", new HashMap<String, Object>() {{
            put("type", "password");
            put("value", keycloakRegister.getPassword());
            put("temporary", false);
        }});
        
        String registrationBody = objectMapper.writeValueAsString(registrationMap);

        System.out.println(registrationBody);

        HttpEntity<String> entity = new HttpEntity<>(registrationBody, registrationHeaders);
       ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
//
//
//        KeycloakTokenDTO token = objectMapper.readValue(response.getBody(), KeycloakTokenDTO.class);
//
//        Cookie cookie = new Cookie("token", token.getAccess_token());
//        cookie.setSecure(true);
//        cookie.setHttpOnly(false);
//        cookie.setMaxAge(7 * 24 * 60 * 60);
//        cookie.setPath("/");
//        res.addCookie(cookie);
        return ResponseEntity.ok(true);
    }


}
