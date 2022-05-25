package id.msams.webrepo.dto.sec;

import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;

import id.msams.webrepo.dto.abs.BaseModelDto;
import id.msams.webrepo.dto.dat.LecturerDto;
import id.msams.webrepo.dto.dat.StudentDto;
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
public class UserPrincipalDto extends BaseModelDto<Long>
    implements org.springframework.security.core.userdetails.UserDetails {

  private String username;
  @JsonIgnore
  private String password;

  private Boolean active;

  private Set<RoleDto> roles;

  private UserDetailsDto userDetails;

  private LecturerDto lecturer;
  private StudentDto student;

  @JsonIgnore
  public boolean isLecturer() {
    return lecturer != null;
  }

  @JsonIgnore
  public boolean isStudent() {
    return student != null;
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.getRoles();
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return this.active.booleanValue();
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return this.active.booleanValue();
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return this.active.booleanValue();
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return this.active.booleanValue();
  }

}
