package com.app.volunteer.security;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.app.volunteer.exception.CustomException;
import com.app.volunteer.model.UserRole;
import com.app.volunteer.model.Users;
import com.app.volunteer.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  //@Value("${security.jwt.token.expire-length:3600000}")
  private long validityInMilliseconds = 360000000;

  @Autowired
  private MyUserDetails myUserDetails;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username, Users appUser) {
    List<GrantedAuthority> roles = new ArrayList<>(appUser.getAppUserRoles().size());
    for (UserRole appRole: appUser.getAppUserRoles()) {
      roles.add(new SimpleGrantedAuthority(appRole.getAuthority()));
    }

    Claims claims = Jwts.claims().setSubject(username);
    claims.put("auth", roles);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public Boolean isRoleExist(String role){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role))) {
      return true;
    }
    return false;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
