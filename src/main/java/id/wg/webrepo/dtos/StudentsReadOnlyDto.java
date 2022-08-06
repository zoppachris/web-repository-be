package id.wg.webrepo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsReadOnlyDto extends BaseModelDto {
    public Long studentId;
    public String studentName;
    public String nim;
    public String year;
}
