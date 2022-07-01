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
    public FacultiesDto faculties;
    public UsersDto users;
    public String password;
    public boolean status;
}
