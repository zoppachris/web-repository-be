package id.msams.webrepo.dto.dat;

import id.msams.webrepo.dto.abs.BaseModelDto;
import id.msams.webrepo.dto.sec.UserPrincipalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto extends BaseModelDto<Long> {

  private String nim;
  private Integer year;

  private UserPrincipalDto user;

}
