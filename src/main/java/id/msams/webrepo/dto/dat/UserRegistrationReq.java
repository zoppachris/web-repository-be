package id.msams.webrepo.dto.dat;

import java.util.Map;

import com.toedter.spring.hateoas.jsonapi.JsonApiTypeForClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonApiTypeForClass("user-registration-request")
public class UserRegistrationReq {

  private String username;
  private String password;

  private String name;
  private String pic;

  private Map<String, Object> etc;

}
