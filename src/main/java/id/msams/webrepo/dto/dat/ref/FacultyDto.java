package id.msams.webrepo.dto.dat.ref;

import id.msams.webrepo.dto.abs.BaseModelDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class FacultyDto extends BaseModelDto<Long> {

  private String name;

}
