package id.wg.webrepo.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUserDto extends BaseModelDto {
    public Long roleUserId;
    public UsersDto user;
    public RolesDto role;
}
