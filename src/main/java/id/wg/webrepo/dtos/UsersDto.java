package id.wg.webrepo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={"password"}, allowSetters= true)
public class UsersDto extends BaseModelDto {
    public Long userId;
    public String userName;
    public String name;
    public String password;
    public String roleName;
    public boolean status;
}
