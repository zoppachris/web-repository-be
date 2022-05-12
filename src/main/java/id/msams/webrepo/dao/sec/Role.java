package id.msams.webrepo.dao.sec;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import id.msams.webrepo.dao.abs.BaseModel;
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
public class Role extends BaseModel implements org.springframework.security.core.GrantedAuthority {

  @Enumerated(EnumType.STRING)
  @Column(unique = true, nullable = false)
  private RoleType name;

  @EqualsAndHashCode.Exclude
  @ManyToMany(mappedBy = "roles")
  private Set<UserPrincipal> users;

  @Override
  public String getAuthority() {
    return this.name.authorityName();
  }

}
