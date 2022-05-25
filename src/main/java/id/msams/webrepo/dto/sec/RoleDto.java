package id.msams.webrepo.dto.sec;

import java.util.Set;

import id.msams.webrepo.dto.abs.BaseModelDto;
import id.msams.webrepo.dao.sec.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends BaseModelDto<Long> implements org.springframework.security.core.GrantedAuthority {

  private RoleType name;

  private Set<UserPrincipalDto> users;

  @Override
  public String getAuthority() {
    return this.name.authorityName();
  }

}
