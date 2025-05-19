package ru.shelter.dto.jwt;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.shelter.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setName(claims.getSubject());
        jwtInfoToken.setId(claims.get("id", Long.class));
        jwtInfoToken.setRole(getRole(claims));
        return jwtInfoToken;
    }

    private static User.Role getRole(Claims claims) {
        final String role = claims.get("role", String.class);
        return role != null ? User.Role.valueOf(role) : null;
    }
}