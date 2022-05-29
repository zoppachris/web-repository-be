package id.msams.webrepo.srv.abs;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import id.msams.webrepo.dao.abs.BaseModel;
import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

public interface CrudService<K extends Serializable, T extends BaseModel<K>> {

  JpaSpecificationRepository<T, K> repo();

  public ResponseEntity<RepresentationModel<?>> findAll(Specification<T> spec, Pageable paging);

  public ResponseEntity<RepresentationModel<?>> findById(K id);

  public ResponseEntity<RepresentationModel<?>> save(@Nullable K id, T data, SaveType strategy);

  public default ResponseEntity<RepresentationModel<?>> save(@Nullable K id, T data) {
    return this.save(id, data, SaveType.UPSERT);
  }

  public ResponseEntity<RepresentationModel<?>> delete(K id);

}
