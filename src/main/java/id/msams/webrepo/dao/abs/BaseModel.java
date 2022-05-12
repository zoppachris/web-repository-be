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
public abstract class BaseModel implements Serializable {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "created_by", nullable = true, updatable = false)
  @CreatedBy
  private UserPrincipal createdBy;
  @Column(nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime createdOn;

  @ManyToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "modified_by", nullable = true, updatable = true)
  @LastModifiedBy
  private UserPrincipal modifiedBy;
  @Column(nullable = true, updatable = true)
  @LastModifiedDate
  private LocalDateTime modifiedOn;

}
