package br.com.postech.fiap.telemedicine.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        try {
            if (authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring("Bearer ".length());
                Claims c = Jwts.parser().setSigningKey("i4pXmYQVXl5uxMbQjwX+C0mOBAgAdf8tD/qAX7IR6u8=").parseClaimsJws(token).getBody();
                String role = c.get("role", String.class);
                Integer id = c.get("id", Integer.class);
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

                UsernamePasswordAuthenticationToken authToken =new UsernamePasswordAuthenticationToken(id,null, List.of(authority));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);
            } else {
                throw new ServletException("Missing or invalid Authorization header");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + "Failed to process token on request." + "\"}");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return
                request.getRequestURI().equals("/telemedicine/users/doctor/register")
                        || request.getRequestURI().equals("/telemedicine/users/patient/register")
                        || request.getRequestURI().equals("/telemedicine/users/login")
                        || request.getRequestURI().equals("/telemedicine/v3/api-docs")
                        || request.getRequestURI().contains("swagger-ui")
                ;
    }
}
