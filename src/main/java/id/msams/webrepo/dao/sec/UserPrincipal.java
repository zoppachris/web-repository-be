package id.msams.webrepo.dao.sec;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.toedter.spring.hateoas.jsonapi.JsonApiTypeForClass;

import org.springframework.security.core.GrantedAuthority;

import id.msams.webrepo.dao.abs.BaseModel;
import id.msams.webrepo.dao.dat.Lecturer;
import id.msams.webrepo.dao.dat.Student;
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
@Entity
@Table
@JsonApiTypeForClass(value = "user")
public class UserPrincipal extends BaseModel<Long>
    implements org.springframework.security.core.userdetails.UserDetails {

  @Column(unique = true, nullable = false)
  private String username;
  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private Boolean active;

  @ManyToMany
  @JoinTable(name = "user_roles")
  private Set<Role> roles;

  @OneToOne(mappedBy = "user", optional = false, cascade = CascadeType.ALL)
  private UserDetails userDetails;

  @OneToOne(mappedBy = "user", optional = true, cascade = CascadeType.ALL)
  private Lecturer lecturer;
  @OneToOne(mappedBy = "user", optional = true, cascade = CascadeType.ALL)
  private Student student;

  public boolean isLecturer() {
    return lecturer != null;
  }

  public boolean isStudent() {
    return student != null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.getRoles();
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.active.booleanValue();
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.active.booleanValue();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.active.booleanValue();
  }

  @Override
  public boolean isEnabled() {
    return this.active.booleanValue();
  }

}
