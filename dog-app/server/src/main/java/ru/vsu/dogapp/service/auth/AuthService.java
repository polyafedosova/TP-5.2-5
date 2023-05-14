package ru.vsu.dogapp.service.auth;


import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.dogapp.dto.jwt.JwtAuthentication;
import ru.vsu.dogapp.dto.jwt.JwtRequest;
import ru.vsu.dogapp.dto.jwt.JwtResponse;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.service.OwnerService;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final OwnerService ownerService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final Owner owner = (Owner) ownerService.loadUserByUsername(authRequest.getUsername());

        if (passwordEncoder.matches(authRequest.getPassword(), owner.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(owner);
            final String refreshToken = jwtProvider.generateRefreshToken(owner);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Invalid password");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();

            final Owner owner = (Owner) ownerService.loadUserByUsername(username);
            final String accessToken = jwtProvider.generateAccessToken(owner);
            return new JwtResponse(accessToken, null);
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();

            final Owner owner = (Owner) ownerService.loadUserByUsername(username);
            final String accessToken = jwtProvider.generateAccessToken(owner);
            final String newRefreshToken = jwtProvider.generateRefreshToken(owner);
            return new JwtResponse(accessToken, newRefreshToken);

        }
        throw new AuthException("Invalid JWT token");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
