package id.msams.webrepo.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import id.msams.webrepo.cfg.prp.SecurityProp;
import id.msams.webrepo.srv.AppUserDetailsSrvc;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

  private final SecurityProp securityProp;
  private final PasswordEncoder passwordEncoder;
  private final AppUserDetailsSrvc appUserDetailsSrvc;

  private static final String SYSTEM_ADMIN_ROLE_NAME = "SYSTEM_ADMIN";

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeRequests()
        .anyRequest().hasRole(SYSTEM_ADMIN_ROLE_NAME)
        .and()
      .csrf()
        .disable()
      .httpBasic()
    ;
    http
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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
      .userDetailsService(appUserDetailsSrvc)
        .passwordEncoder(passwordEncoder)
    ;
    // @formatter:on
  }

}
