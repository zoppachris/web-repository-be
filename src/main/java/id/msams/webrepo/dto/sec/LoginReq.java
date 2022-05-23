package id.msams.webrepo.dto.sec;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {

  @Schema(example = "user123")
  private String username;
  @Schema(example = "secret123")
  private String password;

}
