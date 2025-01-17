package com.smen.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smen.DTO.Keycloak.KeycloakLoginDTO;
import com.smen.DTO.Keycloak.KeycloakRegisterDTO;
import com.smen.DTO.Keycloak.KeycloakTokenDTO;
import com.smen.Services.RoleService;
import com.smen.Services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/keycloak")
@CrossOrigin("*")
@SessionAttributes("token")
public class KeycloakController {

    private final UserService userService;
    private final RoleService roleService;
    @Value("${app.api.keycloak_url}")
    private String KEYCLOAK_URL;

    @Value("${app.api.keycloak_client}")
    private String KEYCLOAK_CLIENT;

    public KeycloakController(UserService userService, JwtDecoder jwtDecoder, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping(path="/login")
    public ResponseEntity<KeycloakTokenDTO> loginUser(@RequestBody KeycloakLoginDTO keycloakLogin) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = KEYCLOAK_URL + "/realms/smen/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "grant_type=password&client_id="+ KEYCLOAK_CLIENT +"&client_secret=URVyOvbZ4S0gwxHHKogLql2PY6bBqB0T&username="
                + keycloakLogin.getUsername()
                + "&password="+keycloakLogin.getPassword();

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        KeycloakTokenDTO token = objectMapper.readValue(response.getBody(), KeycloakTokenDTO.class);

        KeycloakTokenDTO keycloakTokenDTO = new KeycloakTokenDTO();
        keycloakTokenDTO.setAccess_token(token.getAccess_token());
        keycloakTokenDTO.setRole("RANDOM_ROLE");
        return ResponseEntity.ok(keycloakTokenDTO);
    }

    @PostMapping(path="/register")
    public ResponseEntity<KeycloakTokenDTO> registerUser(@RequestBody KeycloakRegisterDTO keycloakRegisterDTO) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Get the admin access token
        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate accessTokenTemplate = new RestTemplate();
        String accessTokenUrl = KEYCLOAK_URL + "/realms/smen/protocol/openid-connect/token";
        String accessTokenBody = "grant_type=client_credentials&client_id=" + KEYCLOAK_CLIENT + "&client_secret=URVyOvbZ4S0gwxHHKogLql2PY6bBqB0T";

        HttpEntity<String> accessTokenEntity = new HttpEntity<>(accessTokenBody, accessTokenHeaders);
        ResponseEntity<String> accessTokenResponse = accessTokenTemplate.postForEntity(accessTokenUrl, accessTokenEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        KeycloakTokenDTO accessToken = objectMapper.readValue(accessTokenResponse.getBody(), KeycloakTokenDTO.class);

        // Setup headers for user registration
        HttpHeaders registrationHeaders = new HttpHeaders();
        registrationHeaders.setContentType(MediaType.APPLICATION_JSON);
        registrationHeaders.setBearerAuth(accessToken.getAccess_token());

        // Construct the registration request body
        Map<String, Object> registrationMap = new HashMap<>();
        registrationMap.put("username", keycloakRegisterDTO.getUsername());
        registrationMap.put("enabled", true);
        registrationMap.put("firstName", keycloakRegisterDTO.getFirstName());
        registrationMap.put("lastName", keycloakRegisterDTO.getLastName());
        registrationMap.put("email", keycloakRegisterDTO.getEmail());
        registrationMap.put("emailVerified", false);

        // Properly format the "credentials" field as a List
        Map<String, Object> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", keycloakRegisterDTO.getPassword());
        credential.put("temporary", false);

        registrationMap.put("credentials", List.of(credential));

        String registrationBody = objectMapper.writeValueAsString(registrationMap);

        HttpEntity<String> entity = new HttpEntity<>(registrationBody, registrationHeaders);
        restTemplate.postForEntity(KEYCLOAK_URL + "/admin/realms/smen/users", entity, String.class);

        userService.createUser(keycloakRegisterDTO.toEntity(keycloakRegisterDTO));

        RestTemplate loginTemplate = new RestTemplate();
        String url = KEYCLOAK_URL + "/realms/smen/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "grant_type=password&client_id="+ KEYCLOAK_CLIENT +"&client_secret=URVyOvbZ4S0gwxHHKogLql2PY6bBqB0T&username="
                + keycloakRegisterDTO.getUsername()
                + "&password="+keycloakRegisterDTO.getPassword();

        HttpEntity<String> entityLogin = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = loginTemplate.postForEntity(url, entityLogin, String.class);

        KeycloakTokenDTO token = objectMapper.readValue(response.getBody(), KeycloakTokenDTO.class);

        KeycloakTokenDTO keycloakTokenDTO = new KeycloakTokenDTO();
        keycloakTokenDTO.setAccess_token(token.getAccess_token());

        String roleName = roleService.getById(keycloakRegisterDTO.getRoleId()).get().getName();
        keycloakTokenDTO.setRole(roleName);
        return ResponseEntity.ok(keycloakTokenDTO);
    }

}
