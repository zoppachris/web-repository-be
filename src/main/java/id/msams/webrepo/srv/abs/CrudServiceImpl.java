package id.msams.webrepo.srv.abs;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import id.msams.webrepo.dao.abs.BaseModel;
import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

public class CrudServiceImpl<K extends Serializable, T extends BaseModel<K>, D> implements CrudService<K, T, D> {

  private final ModelMapper mdlMap;
  private final ModelMapper selMdlMap;

  private final JpaSpecificationRepository<T, K> repo;
  private final PagedResourcesAssembler<D> resourcesAssembler;

  private final RepresentationModelAssembler<D, EntityModel<D>> resourceAssembler;

  @Autowired
  public CrudServiceImpl(@Qualifier("modelMapper") ModelMapper mdlMap,
      @Qualifier("selectiveModelMapper") ModelMapper selMdlMap, JpaSpecificationRepository<T, K> repo,
      PagedResourcesAssembler<D> resourcesAssembler) {
    this.mdlMap = mdlMap;
    this.selMdlMap = selMdlMap;

    this.repo = repo;
    this.resourcesAssembler = resourcesAssembler;

    this.resourceAssembler = entity -> EntityModel.of(mdlMap.map(entity, this.projectionType()));
  }

  @Override
  public JpaSpecificationRepository<T, K> repo() {
    return this.repo;
  }

  @Override
  public ResponseEntity<PagedModel<EntityModel<D>>> findAll(Specification<T> spec,
      Pageable page) {
    return ResponseEntity.ok(
        resourcesAssembler.toModel(
            repo.findAll(page)
                .map(item -> mdlMap.map(item, this.projectionType())),
            resourceAssembler));
  }

  @Override
  public ResponseEntity<EntityModel<D>> findById(K id) {
    return ResponseEntity.ok(
        resourceAssembler.toModel(
            mdlMap.map(
                repo.findById(id).orElseThrow(entityNotFound(id)),
                this.projectionType())));
  }

  private T insert(K id, T data) {
    if (id == null || !repo.existsById(id)) {
      return upsert(id, data);
    } else {
      throw entityExisting(id).get();
    }
  }

  private T upsert(K id, T data) {
    T dataToSave = repo.findById(id).orElseGet(() -> {
      data.setId(id);
      return data;
    });
    return repo.save(dataToSave);
  }

  private T delsert(K id, T data) {
    if (repo.existsById(id))
      repo.deleteById(id);
    return upsert(id, data);
  }

  private T update(K id, T data) {
    if (repo.existsById(id)) {
      return upsert(id, data);
    } else {
      throw entityNotFound(id).get();
    }
  }

  private T partial(K id, T data) {
    if (repo.existsById(id)) {
      T existingData = repo.getById(id);
      selMdlMap.map(data, existingData);
      return upsert(id, existingData);
    } else {
      throw entityNotFound(id).get();
    }
  }

  @Override
  public ResponseEntity<EntityModel<D>> save(@Nullable K id, T data, SaveType strategy) {
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
        resourceAssembler.toModel(
            mdlMap.map(savedData, this.projectionType())));
  }

}
