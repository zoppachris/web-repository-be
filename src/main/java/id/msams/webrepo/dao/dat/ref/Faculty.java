package id.msams.webrepo.dao.dat.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.toedter.spring.hateoas.jsonapi.JsonApiTypeForClass;

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
@JsonApiTypeForClass(value = "faculty")
public class Faculty extends BaseModel<Long> {

  @Column(nullable = false)
  private String name;

}
