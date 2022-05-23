package id.msams.webrepo.dto.sec;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRes {

  @Schema(example = "Bearer", description = "Type of credentials given for access. Current implementation only supports Bearer token.")
  private String type;
  @Schema(example = "eyJ…iJ9.eyJ…OX0.FHX…5vg", description = "Access token credential to include within each request. A three-segmented token string delimited with period (.) symbol, implemented using JWT standard.")
  private String accessToken;
  @Schema(ref = "#/components/schemas/jwt-claims")
  private Map<String, Object> claims;

}
