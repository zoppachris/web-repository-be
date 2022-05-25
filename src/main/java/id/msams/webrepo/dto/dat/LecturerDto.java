package id.msams.webrepo.dto.dat;

import id.msams.webrepo.dto.abs.BaseModelDto;
import id.msams.webrepo.dto.dat.ref.FacultyDto;
import id.msams.webrepo.dto.sec.UserPrincipalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LecturerDto extends BaseModelDto<Long> {

  private String nidn;

  private FacultyDto faculty;

  private UserPrincipalDto user;

}
