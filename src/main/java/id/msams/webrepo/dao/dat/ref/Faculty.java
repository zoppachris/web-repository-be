package id.msams.webrepo.dao.dat.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import id.msams.webrepo.dao.abs.BaseModel;
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
public class Faculty extends BaseModel {

  @Column(nullable = false)
  private String name;

}
