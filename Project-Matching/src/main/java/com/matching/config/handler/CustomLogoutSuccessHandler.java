package com.matching.config.handler;

import com.matching.config.exception.InvalidTokenException;
import com.matching.domain.JwtToken;
import com.matching.config.auth.SecurityConstants;
import com.matching.repository.JwtTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);

        token = token.replaceAll("Bearer", "").trim();

        if(jwtTokenRepository.findByToken(token) == null)
            throw new InvalidTokenException("토큰이 유효하지 않습니다.");

        JwtToken jwtToken = jwtTokenRepository.findByToken(token);
        jwtToken.setStatus(false);
        jwtTokenRepository.save(jwtToken);
    }
}
