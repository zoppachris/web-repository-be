package id.wg.webrepo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={"password", "status"}, allowSetters= true)
public class StudentsDto extends BaseModelDto {
    public Long studentId;
    public String studentName;
    public String nim;
    public String year;
    private MajorsDto majors;
    private String password;
    private boolean status;
}
