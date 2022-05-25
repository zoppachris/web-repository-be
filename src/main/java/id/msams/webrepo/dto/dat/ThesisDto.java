package id.msams.webrepo.dto.dat;

import id.msams.webrepo.dto.abs.BaseModelDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ThesisDto extends BaseModelDto<Long> {

  private String title;
  private String abstraction;
  private String keywords;
  private Integer year;
  private String partialDocument;
  private String fullDocument;

  private StudentDto student;

}
