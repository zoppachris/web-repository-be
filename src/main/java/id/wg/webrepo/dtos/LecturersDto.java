package id.wg.webrepo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={"password", "status"}, allowSetters= true)
public class LecturersDto extends BaseModelDto {
    public Long lectureId;
    public String lectureName;
    public String nidn;
    private FacultiesDto faculties;
    private UsersDto users;
    private String password;
    private boolean status;
}
