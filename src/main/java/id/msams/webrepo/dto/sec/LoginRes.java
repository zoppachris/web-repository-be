package id.msams.webrepo.dto.sec;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRes {

  private String type;
  private String accessToken;
  private Map<String, Object> claims;

}
