package com.icd.wksh.securities;

import com.icd.wksh.commons.Util;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class JwtTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMin}")
    private int jwtExpirationInMin;

    @Autowired
    private PasswordEncoder encoder;

    public String generateToken(String username) {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime expiryDate = current.plusMinutes(jwtExpirationInMin);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Util.convertLocalDateTimeToDate(current))
                .setExpiration(Util.convertLocalDateTimeToDate(expiryDate))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken,String ipAddress,String username) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!Util.isEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public String getUserNameFromRequest(HttpServletRequest request) {
        String result = null;
        String authToken = getJwtFromRequest(request);
        JwtParser parser = Jwts.parser().setSigningKey(jwtSecret);
        Jws<Claims> parseClaimsJws = parser.parseClaimsJws(authToken);
        Claims body = parseClaimsJws.getBody();
        result = (String) body.get("sub");
        return result;
    }

    public boolean matchPassword(String nonHash, String hashPassword) {
        return encoder.matches(nonHash, hashPassword);
    }
}
