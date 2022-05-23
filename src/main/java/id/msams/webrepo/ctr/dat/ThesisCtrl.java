package id.msams.webrepo.ctr.dat;

import com.sipios.springsearch.anotation.SearchSpec;
import com.toedter.spring.hateoas.jsonapi.MediaTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.msams.webrepo.dao.dat.Thesis;
import id.msams.webrepo.srv.abs.CrudService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/thesis", produces = MediaTypes.JSON_API_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ThesisCtrl {

  private final CrudService<Thesis, Long, Thesis> svc;

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<Thesis>>> findAll(
      @SearchSpec(searchParam = "search") Specification<Thesis> spec, Pageable page) {
    return svc.findAll(spec, page);
  }

}
