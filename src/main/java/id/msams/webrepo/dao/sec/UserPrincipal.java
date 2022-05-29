package id.msams.webrepo.dao.sec;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserPrincipal extends BaseModel<Long>
    implements org.springframework.security.core.userdetails.UserDetails {

  @Column(unique = true, nullable = false)
  private String username;
  @Column(nullable = false)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @Column(nullable = false)
  private Boolean active;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles")
  private Set<Role> roles;

  @OneToOne(mappedBy = "user", optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private UserDetails userDetails;

  @OneToOne(mappedBy = "user", optional = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private Lecturer lecturer;
  @OneToOne(mappedBy = "user", optional = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private Student student;

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
