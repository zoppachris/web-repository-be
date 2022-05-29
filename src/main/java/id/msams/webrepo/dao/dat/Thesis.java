package id.msams.webrepo.dao.dat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonApiTypeForClass(value = "thesis")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Thesis extends BaseModel<Long> {

  @Column(nullable = false)
  private String title;
  @Column
  private String abstraction;
  @Column
  private String keywords;
  @Column(nullable = false)
  private Integer year;
  @Column
  private String partialDocument;
  @Column(nullable = false)
  private String fullDocument;

  @ManyToOne(optional = false)
  @JoinColumn(nullable = false, updatable = false)
  private Student student;

}
