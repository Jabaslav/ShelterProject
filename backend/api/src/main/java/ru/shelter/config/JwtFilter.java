package ru.shelter.config;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.shelter.dto.jwt.JwtAuthentication;
import ru.shelter.dto.jwt.JwtUtils;
import ru.shelter.impl.JwtService;
import ru.shelter.model.User;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                         HttpServletResponse response,
                         FilterChain fc) throws IOException, ServletException {

        if (request.getRequestURI().startsWith("/api/auth") ||
            request.getRequestURI().equals("/"))
        {
            fc.doFilter(request, response);
            return;
        }

        final String token = getTokenFromRequest((HttpServletRequest) request);
        if (token != null && jwtService.validateAccessToken(token)) {
            log.info("Валидация токена прошла успешно!");
            final Claims claims = jwtService.getAccessClaims(token);

            // Создание объекта аутентификации
            final JwtAuthentication auth = JwtUtils.generate(claims);
            auth.setId(claims.get("id", Long.class)); // Важно!
            auth.setName(claims.getSubject());
            auth.setRole(User.Role.valueOf(claims.get("role", String.class)));
            auth.setAuthenticated(true);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        fc.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}

