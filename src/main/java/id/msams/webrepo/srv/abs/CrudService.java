package id.msams.webrepo.srv.abs;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

public interface CrudService<T, K, D> {

  JpaSpecificationRepository<T, K> repo();

  public ResponseEntity<PagedModel<EntityModel<D>>> findAll(Specification<T> spec, Pageable page);

  default Type getTypeArgument(int i) {
    return ((ParameterizedType) getClass().getGenericSuperclass())
        .getActualTypeArguments()[i];
  }

}
