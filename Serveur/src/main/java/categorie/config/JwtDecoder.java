package categorie.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureException;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtDecoder {

    private static final String JWKS_URI = "http://keycloak:8080/realms/FullStack/protocol/openid-connect/certs";
    private static final String KID = "M8uX5qMC-Uo3M5n8ByOE8obEL_WqohAErdc6QRC94-k";

    public static Claims decodeJWT(String jwt) throws Exception {
        PublicKey publicKey = getPublicKeyFromJWKS(KID);

        JwtParser parser = Jwts.parser()
                .setSigningKey(publicKey)
                .build();
        return parser.parseClaimsJws(jwt).getBody();
    }

    public static boolean isTokenValid(String jwt) {
        try {
            Claims claims = decodeJWT(jwt);

            Date expirationDate = claims.getExpiration();
            if (expirationDate == null || expirationDate.before(new Date())) {
                return false;
            }

            List<String> roles = getRolesFromJwt(claims);
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return true;
        } catch (SignatureException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private static List<String> getRolesFromJwt(Claims claims) {
        Map<String, Object> resourceAccess = claims.get("resource_access", Map.class);
        List<String> roles = (List<String>) ((Map) resourceAccess.get("fullstack-client-id")).get("roles");
        return roles;
    }

    private static PublicKey getPublicKeyFromJWKS(String kid) throws Exception {
        URL url = new URL(JWKS_URI);
        InputStream inputStream = url.openStream();

        Map<String, Object> jwks = new ObjectMapper().readValue(inputStream, Map.class);

        List<Map<String, Object>> keys = (List<Map<String, Object>>) jwks.get("keys");

        String n = (String) keys.stream()
                .filter(k -> k.get("kid").equals(kid))
                .findFirst()
                .get()
                .get("n");

        String e = (String) keys.stream()
                .filter(k -> k.get("kid").equals(kid))
                .findFirst()
                .get()
                .get("e");

        byte[] modulus = Base64.getUrlDecoder().decode(n);
        byte[] exponent = Base64.getUrlDecoder().decode(e);

        RSAPublicKeySpec spec = new RSAPublicKeySpec(new java.math.BigInteger(1, modulus),
                new java.math.BigInteger(1, exponent));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(spec);

        return publicKey;
    }
}