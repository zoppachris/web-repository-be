package id.msams.webrepo.dao.sec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
public class UserDetails extends BaseModel {

  @Column(nullable = false)
  private String name;
  @Column
  private String pic;

  @OneToOne(optional = false)
  @JoinColumn(unique = true, nullable = false, updatable = false)
  private UserPrincipal user;

}
