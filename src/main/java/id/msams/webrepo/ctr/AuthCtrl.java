package id.msams.webrepo.ctr;

import java.security.KeyPair;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.msams.webrepo.ctr.abs.JsonApiRequestMapping;
import id.msams.webrepo.dao.sec.Role;
import id.msams.webrepo.dao.sec.UserPrincipal;
import id.msams.webrepo.dto.sec.LoginReq;
import id.msams.webrepo.dto.sec.LoginRes;
import id.msams.webrepo.ext.i18n.utility.MessageUtil;
import lombok.RequiredArgsConstructor;

@RestController
@JsonApiRequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthCtrl {

  private final UserDetailsService appUserDetailsSrvc;
  private final PasswordEncoder passwordEncoder;
  @Qualifier("rsaKeyPair")
  private final KeyPair keys;

  private final JwtEncoder jwtEncoder;

  private final MessageUtil msg;

  @PostMapping("/login")
  public ResponseEntity<EntityModel<LoginRes>> login(@RequestBody LoginReq body) {
    UserPrincipal user = (UserPrincipal) appUserDetailsSrvc.loadUserByUsername(body.getUsername());
    if (!passwordEncoder.matches(body.getPassword(), user.getPassword()))
      throw new UsernameNotFoundException(msg.get("SYSTEM.ERROR.AUTH.INVALID-CREDENTIALS.DETAIL"));

    LocalDateTime issuedTimestamp = LocalDateTime.now();
    Jwt jwt = jwtEncoder.encode(JwtEncoderParameters
        .from(JwtClaimsSet.builder()
            // from
            .issuer("self")
            .issuedAt(issuedTimestamp.toInstant(ZoneOffset.UTC))
            // for
            .audience(Arrays.asList("self"))
            .expiresAt(issuedTimestamp.plusHours(24).toInstant(ZoneOffset.UTC))
            // of
            .subject(user.getUsername())
            .claim("scp", user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet()))
            .build()));

    return ResponseEntity.ok(
        EntityModel.of(
            LoginRes.builder()
                .id(System.currentTimeMillis())
                .type("Bearer")
                .accessToken(jwt.getTokenValue())
                .claims(jwt.getClaims())
                .build()));
  }

}
