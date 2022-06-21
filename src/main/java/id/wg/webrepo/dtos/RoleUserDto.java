package id.wg.webrepo.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUserDto extends BaseModelDto {
    public Long roleUserId;
    private UsersDto user;
    private RolesDto role;
}
