package id.msams.webrepo.ctr.dat;

import com.sipios.springsearch.anotation.SearchSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import id.msams.webrepo.ctr.abs.JsonApiRequestMapping;
import id.msams.webrepo.dao.dat.Thesis;
import id.msams.webrepo.dto.dat.ThesisDto;
import id.msams.webrepo.srv.abs.CrudService;
import lombok.RequiredArgsConstructor;

@RestController
@JsonApiRequestMapping(path = "/thesis")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ThesisCtrl {

  private final CrudService<Long, Thesis, ThesisDto> svc;

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<ThesisDto>>> findAll(
      @SearchSpec(searchParam = "search") Specification<Thesis> spec, Pageable page) {
    return svc.findAll(spec, page);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EntityModel<ThesisDto>> findAll(@PathVariable("id") Long id) {
    return svc.findById(id);
  }

}
