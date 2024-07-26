package br.com.postech.fiap.telemedicine;

import br.com.postech.fiap.telemedicine.entities.User;
import br.com.postech.fiap.telemedicine.enums.UserType;
import br.com.postech.fiap.telemedicine.service.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    public void testGenerateToken() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setType(UserType.PATIENT);

        String token = jwtUtil.generateToken(user);
        assertNotNull(token);

        Claims claims = Jwts.parser()
                .setSigningKey("i4pXmYQVXl5uxMbQjwX+C0mOBAgAdf8tD/qAX7IR6u8=")
                .parseClaimsJws(token)
                .getBody();

        assertEquals("John Doe", claims.getSubject());
        assertEquals(1L, claims.get("id", Long.class));
        assertEquals(UserType.PATIENT.toString(), claims.get("role"));
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    public void testCreateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", UserType.PATIENT);
        claims.put("id", 1L);

        String token = jwtUtil.createToken(claims, "John Doe");
        assertNotNull(token);

        Claims parsedClaims = Jwts.parser()
                .setSigningKey("i4pXmYQVXl5uxMbQjwX+C0mOBAgAdf8tD/qAX7IR6u8=")
                .parseClaimsJws(token)
                .getBody();

        assertEquals("John Doe", parsedClaims.getSubject());
        assertEquals(1L, parsedClaims.get("id", Long.class));
        assertEquals(UserType.PATIENT.toString(), parsedClaims.get("role"));
        assertTrue(parsedClaims.getExpiration().after(new Date()));
    }
}