package id.msams.webrepo.dao.abs;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import id.msams.webrepo.dao.sec.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel<K extends Serializable> implements Serializable {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private K id;

  @ManyToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "created_by", nullable = true, updatable = false)
  @CreatedBy
  private UserPrincipal createdBy;
  @Column(nullable = false, updatable = false)
  @CreatedDate
  @JsonFormat(shape = Shape.STRING)
  private LocalDateTime createdOn;

  @ManyToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "modified_by", nullable = true, updatable = true)
  @LastModifiedBy
  private UserPrincipal modifiedBy;
  @Column(nullable = true, updatable = true)
  @LastModifiedDate
  @JsonFormat(shape = Shape.STRING)
  private LocalDateTime modifiedOn;

}
