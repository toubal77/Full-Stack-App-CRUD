package categorie.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtDecoder {

    private static final String SECRET_KEY = "a8G6SAJBfBhyqJYduDLYVc6FArIYHIhb";

    // public static Claims decodeJWT(String jwt) {
    // SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));

    // return Jwts.parser()
    // .setSigningKey(key)
    // .parseClaimsJws(jwt)
    // .getBody();
    // }
}