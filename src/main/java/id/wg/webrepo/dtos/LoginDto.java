package id.wg.webrepo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={"password"}, allowSetters= true)
public class LoginDto {
    public long userId;
    public Long thesesId;
    public String username;
    public String name;
    public String roleName;
    public String majorName;
    public String facultyName;
    public String password;
    public String token;
    public String type;
    public Long expire;
}
