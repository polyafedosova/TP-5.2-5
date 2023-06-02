package ru.vsu.dogapp.service.auth;


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
            return new JwtResponse(accessToken);
        } else {
            throw new AuthException("Invalid password");
        }
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
