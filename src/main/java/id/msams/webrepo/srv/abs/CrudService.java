package id.msams.webrepo.srv.abs;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Supplier;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import id.msams.webrepo.dao.abs.BaseModel;
import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

public interface CrudService<K extends Serializable, T extends BaseModel<K>, D> {

  JpaSpecificationRepository<T, K> repo();

  public ResponseEntity<PagedModel<EntityModel<D>>> findAll(Specification<T> spec, Pageable page);

  public ResponseEntity<EntityModel<D>> findById(K id);

  public ResponseEntity<EntityModel<D>> save(@Nullable K id, T data, SaveType strategy);

  public default ResponseEntity<EntityModel<D>> save(@Nullable K id, T data) {
    return this.save(id, data, SaveType.UPSERT);
  }

  default Supplier<EntityNotFoundException> entityNotFound(K id) {
    return () -> new EntityNotFoundException(
        "Entity of type " + this.getTypeArgument(0) + " with id " + id + " could not be found");
  }

  default Supplier<EntityExistingException> entityExisting(K id) {
    return () -> new EntityExistingException(
        "Entity of type " + this.getTypeArgument(0) + " with id " + id + " already exists");
  }

  @SuppressWarnings("unchecked")
  default <U extends Type> U getTypeArgument(int i) {
    return (U) ((ParameterizedType) getClass().getGenericSuperclass())
        .getActualTypeArguments()[i];
  }

  default Class<T> modelType() {
    return this.getTypeArgument(0);
  }

  default Class<K> keyType() {
    return this.getTypeArgument(1);
  }

  default Class<D> projectionType() {
    return this.getTypeArgument(2);
  }

}
