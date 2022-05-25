package id.msams.webrepo.dto.sec;

import id.msams.webrepo.dto.abs.BaseModelDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto extends BaseModelDto<Long> {

  private String name;
  private String pic;

  private UserPrincipalDto user;

}
