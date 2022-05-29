package id.msams.webrepo.dto.sec;

import com.toedter.spring.hateoas.jsonapi.JsonApiTypeForClass;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonApiTypeForClass(value = "login-request")
public class LoginReq {

  @Schema(example = "user123")
  private String username;
  @Schema(example = "secret123")
  private String password;

}
