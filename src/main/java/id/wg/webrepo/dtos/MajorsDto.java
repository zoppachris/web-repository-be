package id.wg.webrepo.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorsDto extends BaseModelDto {
    public Long majorId;
    public String majorName;
    public FacultiesDto faculties;
}
