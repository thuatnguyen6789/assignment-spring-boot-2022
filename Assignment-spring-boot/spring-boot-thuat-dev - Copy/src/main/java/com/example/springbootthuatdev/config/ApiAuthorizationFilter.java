package com.example.springbootthuatdev.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springbootthuatdev.util.JwtUtil;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ApiAuthorizationFilter extends OncePerRequestFilter {
    private static final String[] IGNORE_PATHS = {"/api/v1/login", "/api/v1/register", "/api/v1/products"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // neu client goi den link dang nhap, dang ki va nhung link khong can check login
        String requestPath = request.getServletPath();
        if(Arrays.asList(IGNORE_PATHS).contains(requestPath)) {
            // cho qua
            filterChain.doFilter(request, response);
            return;
        }
        // truong hop client khong co request header theo format can thiet
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            // cho qua khong co dau
            filterChain.doFilter(request, response);
            return;
        }
        // lay thong tin token tu trong request header
        try {
            // remove chu Bearer
            String token = authorizationHeader.replace("Bearer", "").trim();
            // dich nguoc JWT
            DecodedJWT decodedJWT = JwtUtil.getDecodedJwt(token);
            // lay thong tin username
            String username = decodedJWT.getSubject();
            // lay thong tin role dang nhap
            String role = decodedJWT.getClaim(JwtUtil.ROLE_CLAIM_KEY).asString();
            // tao danh sach role
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));
            // luu thong tin nguoi dung dang nhap vao spring context
            // de cho cac controller hay filter phia sau co the su dung
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // dua request di tiep
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            // show error
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String, String> errors = new HashMap<>();
            errors.put("error", ex.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().println(new Gson().toJson(errors));
        }
    }
}
