package categorie.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;

import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Map;

public class JwtDecoder {

    private static final String JWKS_URI = "http://keycloak:8080/realms/FullStack/protocol/openid-connect/certs";

    public static Claims decodeJWT(String jwt) throws Exception {
        PublicKey publicKey = getPublicKeyFromJWKS("M8uX5qMC-Uo3M5n8ByOE8obEL_WqohAErdc6QRC94-k");

        JwtParser parser = Jwts.parser()
                .setSigningKey(publicKey)
                .build();
        return parser.parseClaimsJws(jwt).getBody();
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