package categorie.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import categorie.DTO.LoginResponseDTO;
import categorie.config.JwtDecoder;
import io.jsonwebtoken.Claims;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        String tokenUrl = "http://keycloak:8080/realms/FullStack/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, Map.class);

            String accessToken = (String) response.getBody().get("access_token");

            if (accessToken != null) {
                Claims claims = JwtDecoder.decodeJWT(accessToken);

                String emailVerified = claims.get("email_verified").toString();
                String name = claims.get("name").toString();
                String preferredUsername = claims.get("preferred_username").toString();
                String givenName = claims.get("given_name").toString();
                String familyName = claims.get("family_name").toString();
                String email = claims.get("email").toString();

                LoginResponseDTO loginResponse = new LoginResponseDTO(emailVerified == "true" ? true : false, name,
                        preferredUsername, givenName, familyName, email);

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("Authorization", "Bearer " + accessToken);

                return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No access token found in the response");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials" + e.toString());
        }
    }
}