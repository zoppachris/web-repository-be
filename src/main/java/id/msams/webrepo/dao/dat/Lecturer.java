package id.msams.webrepo.dao.dat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.toedter.spring.hateoas.jsonapi.JsonApiTypeForClass;

import id.msams.webrepo.dao.abs.BaseModel;
import id.msams.webrepo.dao.dat.ref.Faculty;
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
@JsonApiTypeForClass(value = "lecturer")
public class Lecturer extends BaseModel<Long> {

  @Column(nullable = false)
  private String nidn;

  @ManyToOne(optional = false)
  @JoinColumn(nullable = false, updatable = false)
  private Faculty faculty;

  @OneToOne(optional = false)
  @JoinColumn(unique = true, nullable = false, updatable = false)
  private UserPrincipal user;

}
