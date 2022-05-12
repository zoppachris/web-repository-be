package id.msams.webrepo.dao.dat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import id.msams.webrepo.dao.abs.BaseModel;
import id.msams.webrepo.dao.sec.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Student extends BaseModel {

  @Column(nullable = false)
  private String nim;
  @Column(nullable = false)
  private Integer year;

  @OneToOne(optional = false)
  @JoinColumn(unique = true, nullable = false, updatable = false)
  private UserPrincipal user;

}
