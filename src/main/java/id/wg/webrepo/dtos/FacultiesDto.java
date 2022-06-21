package id.wg.webrepo.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultiesDto extends BaseModelDto {
    public Long facultyId;
    public String facultyName;
}
