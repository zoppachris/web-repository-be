package id.msams.webrepo.srv.abs;

import java.io.Serializable;
import java.util.function.Supplier;

import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import id.msams.webrepo.dao.abs.BaseModel;
import id.msams.webrepo.dao.abs.JpaSpecificationRepository;
import id.msams.webrepo.ext.i18n.utility.MessageUtil;

public class CrudServiceImpl<K extends Serializable, T extends BaseModel<K>> implements CrudService<K, T> {

  private final ModelMapper selMdlMap;
  private final JpaSpecificationRepository<T, K> repo;
  private final MessageUtil msg;

  @Autowired
  public CrudServiceImpl(@Qualifier("selectiveModelMapper") ModelMapper selMdlMap,
      JpaSpecificationRepository<T, K> repo, MessageUtil msg) {
    this.selMdlMap = selMdlMap;
    this.repo = repo;
    this.msg = msg;
  }

  private Supplier<EntityNotFoundException> entityNotFound(K id) {
    return () -> new EntityNotFoundException(msg.get("SYSTEM.ERROR.ENTITY.NOT-FOUND.DETAIL", id));
  }

  private Supplier<EntityExistingException> entityExisting(K id) {
    return () -> new EntityExistingException(msg.get("SYSTEM.ERROR.ENTITY.EXISTING.DETAIL", id));
  }

  @Override
  public JpaSpecificationRepository<T, K> repo() {
    return this.repo;
  }

  @Override
  public ResponseEntity<RepresentationModel<?>> findAll(Specification<T> spec,
      Pageable paging) {
    Page<T> page = repo.findAll(paging);
    return ResponseEntity.ok(
        JsonApiModelBuilder.jsonApiModel()
            .model(
            PagedModel.of(
                page.getContent(),
                new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements())))
            .build());
  }

  @Override
  public ResponseEntity<RepresentationModel<?>> findById(K id) {
    return ResponseEntity.ok(
        JsonApiModelBuilder.jsonApiModel()
            .model(
            repo.findById(id)
                .orElseThrow(
                    entityNotFound(id)))
            .build());
  }

  private T insert(K id, T data) {
    if (id == null || !repo.existsById(id)) {
      return upsert(id, data);
    } else {
      throw entityExisting(id).get();
    }
  }

  private T upsert(K id, T data) {
    T dataToSave;
    if (id != null) {
      dataToSave = repo.findById(id).orElseGet(() -> {
        data.setId(id);
        return data;
      });
    } else {
      data.setId(id);
      dataToSave = data;
    }
    return repo.save(dataToSave);
  }

  private T delsert(K id, T data) {
    if (id != null && repo.existsById(id))
      repo.deleteById(id);
    return upsert(id, data);
  }

  private T update(K id, T data) {
    if (id != null && repo.existsById(id)) {
      return upsert(id, data);
    } else {
      throw entityNotFound(id).get();
    }
  }

  private T partial(K id, T data) {
    if (id != null && repo.existsById(id)) {
      T existingData = repo.getById(id);
      selMdlMap.map(data, existingData);
      return upsert(id, existingData);
    } else {
      throw entityNotFound(id).get();
    }
  }

  @Override
  public ResponseEntity<RepresentationModel<?>> save(@Nullable K id, T data, SaveType strategy) {
    T savedData;
    switch (strategy) {
      case INSERT:
        savedData = insert(id, data);
        break;
      case UPSERT:
        savedData = upsert(id, data);
        break;
      case DELSERT:
        savedData = delsert(id, data);
        break;
      case UPDATE:
        savedData = update(id, data);
        break;
      case PARTIAL:
        savedData = partial(id, data);
        break;
      default:
        throw new UnsupportedOperationException("Unknown saving strategy");
    }
    return ResponseEntity.ok(
        JsonApiModelBuilder.jsonApiModel()
            .model(savedData)
            .build());
  }

  @Override
  public ResponseEntity<RepresentationModel<?>> delete(K id) {
    if (!repo.existsById(id))
      throw this.entityNotFound(id).get();

    repo.deleteById(id);
    return ResponseEntity.ok(RepresentationModel.of(null));
  }

}
