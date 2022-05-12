package id.msams.webrepo.cfg.prp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("application.security")
public class SecurityProp {
  private Integer strength = 16;
  private boolean defaultLoginEnabled = false;
  private String defaultLogin = "admin";
  private String defaultPassword = "admin";
}
