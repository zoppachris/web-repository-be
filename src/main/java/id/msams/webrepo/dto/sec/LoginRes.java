package id.msams.webrepo.dto.sec;

import java.util.Map;

import com.toedter.spring.hateoas.jsonapi.JsonApiId;
import com.toedter.spring.hateoas.jsonapi.JsonApiTypeForClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonApiTypeForClass(value = "login-response")
public class LoginRes {

  @JsonApiId
  private Long id;

  private String type;
  private String accessToken;
  private Map<String, Object> claims;

}
