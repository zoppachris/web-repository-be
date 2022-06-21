package id.wg.webrepo.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesDto extends BaseModelDto {
    public Long roleId;
    public String roleName;
}
