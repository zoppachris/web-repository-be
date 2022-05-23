package id.msams.webrepo.srv.abs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import id.msams.webrepo.dao.abs.JpaSpecificationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrudServiceImpl<T, K, D> implements CrudService<T, K, D> {

  private final ModelMapper mdlMap;

  private final JpaSpecificationRepository<T, K> repo;

  private final PagedResourcesAssembler<D> resourcesAssembler;

  @Override
  public JpaSpecificationRepository<T, K> repo() {
    return this.repo;
  }

  @Override
  @SuppressWarnings("unchecked")
  public ResponseEntity<PagedModel<EntityModel<D>>> findAll(Specification<T> spec,
      Pageable page) {
    return ResponseEntity.ok(
        resourcesAssembler.toModel(
            repo.findAll(page)
                .map(item -> mdlMap.map(item, (Class<D>) this.getTypeArgument(2)))));
  }

}
