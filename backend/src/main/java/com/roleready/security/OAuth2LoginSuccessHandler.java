package com.roleready.security;

import com.roleready.user.User;
import com.roleready.user.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
  private final UserRepository users;
  private final JwtService jwt;
  private final String frontendUrl;

  public OAuth2LoginSuccessHandler(UserRepository users, JwtService jwt,
      org.springframework.beans.factory.annotation.Value("${app.frontend-url:http://localhost:4200}") String frontendUrl){
    this.users=users;this.jwt=jwt;this.frontendUrl=frontendUrl;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response,Authentication authentication)
      throws IOException, ServletException {
    OAuth2User oauth=(OAuth2User)authentication.getPrincipal();
    String email=oauth.getAttribute("email");
    String sub=oauth.getAttribute("sub");
    if(email==null||sub==null) throw new ServletException("Google account did not provide required identity claims");
    User user=users.findByGoogleSub(sub).orElseGet(()->users.findByEmail(email).orElseGet(User::new));
    user.setEmail(email); user.setGoogleSub(sub); user.setEmailVerified(true); users.save(user);
    String token=jwt.generateToken(user.getId());
    response.sendRedirect(frontendUrl+"/account?oauthToken="+java.net.URLEncoder.encode(token,java.nio.charset.StandardCharsets.UTF_8));
  }
}
