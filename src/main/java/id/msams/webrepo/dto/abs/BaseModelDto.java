package id.msams.webrepo.dto.abs;

import java.io.Serializable;
import java.time.LocalDateTime;

import id.msams.webrepo.dto.sec.UserPrincipalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseModelDto<K extends Serializable> implements Serializable {

  private K id;

  private UserPrincipalDto createdBy;
  private LocalDateTime createdOn;

  private UserPrincipalDto modifiedBy;
  private LocalDateTime modifiedOn;

}
