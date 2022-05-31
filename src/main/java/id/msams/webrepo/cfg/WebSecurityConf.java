package id.msams.webrepo.cfg;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import id.msams.webrepo.dao.sec.RoleType;
import id.msams.webrepo.srv.AppUserDetailsSrvc;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

  private final SecurityProp securityProp;
  private final PasswordEncoder passwordEncoder;
  private final AppUserDetailsSrvc userDetailsSvc;

  private static final String SYSTEM_ADMIN_ROLE_NAME = "SYSTEM_ADMIN";

  @Bean
  public KeyPair rsaKeyPair() throws NoSuchAlgorithmException {
    return KeyPairGenerator.getInstance("RSA").generateKeyPair();
  }

  @Bean
  public JwtEncoder nimbusRsaJwtEncoder(@Qualifier("rsaKeyPair") KeyPair keys) {
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(
        new JWKSet(
            new RSAKey.Builder((RSAPublicKey) keys.getPublic())
                .privateKey((RSAPrivateKey) keys.getPrivate())
                .build())));
  }

  @Bean
  public JwtDecoder nimbusRsaJwtDecoder(@Qualifier("rsaKeyPair") KeyPair keys) {
    return NimbusJwtDecoder.withPublicKey((RSAPublicKey) keys.getPublic()).build();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeRequests()
        //*
        // login bisa diakses semuanya
        .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
        // get untuk thesis, faculty, dan major, bisa diakses semuanya
        .antMatchers(HttpMethod.GET, "/theses/**", "/faculties/**", "/majors/**").permitAll()
        // api explorer dibolehin untuk semua orang
        .antMatchers("/oas/**", "/swagger/**", "/swagger-ui/**", "/explorer/**").permitAll()
        // system admin dan super admin boleh akses segalanya
        .antMatchers("/**").hasAnyRole(SYSTEM_ADMIN_ROLE_NAME, RoleType.SUPER_ADMIN.roleName())
        // admin punya akses penuh untuk manipulasi user, faculty, sama major
        .antMatchers("/users/**", "/faculties/**", "/majors/**").hasRole(RoleType.ADMIN.roleName())
        // student punya akses untuk ngebuat dan ngedit thesis
        .antMatchers(HttpMethod.POST, "/theses/**").hasRole(RoleType.STUDENT.roleName())
        .antMatchers(HttpMethod.PUT, "/theses/**").hasRole(RoleType.STUDENT.roleName())
        // lecturer punya akses penuh untuk thesis
        .antMatchers("/theses/**").hasRole(RoleType.LECTURER.roleName())
        // sisanya, bisa diakses asalkan udah login
        .anyRequest().authenticated()
        /*/
        .anyRequest().permitAll()
        //*/
        .and()
      .cors().and()
      .csrf().disable()
    ;

    http
      .httpBasic()
        .and()
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    ;

    JwtGrantedAuthoritiesConverter jwtGrantConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantConverter.setAuthorityPrefix("");

    JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
    jwtAuthConverter.setJwtGrantedAuthoritiesConverter(jwtGrantConverter);

    http
      .oauth2ResourceServer()
        .jwt(config -> config.jwtAuthenticationConverter(jwtAuthConverter))
    ;
    // @formatter:on
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // @formatter:off
    if(securityProp.isDefaultLoginEnabled()){
      auth
        .inMemoryAuthentication()
          .passwordEncoder(passwordEncoder)
          .withUser(securityProp.getDefaultLogin())
            .password(passwordEncoder.encode(securityProp.getDefaultPassword()))
            .roles(SYSTEM_ADMIN_ROLE_NAME)
      ;
    }
    auth
      .userDetailsService(userDetailsSvc)
        .passwordEncoder(passwordEncoder)
    ;
    // @formatter:on
  }

}
