package id.wg.webrepo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto extends BaseModelDto {
    public Long userId;
    public String userName;
    public String name;
    public String password;
    public boolean status;
}
