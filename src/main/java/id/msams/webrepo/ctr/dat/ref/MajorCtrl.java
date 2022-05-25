package id.msams.webrepo.ctr.dat.ref;

import com.sipios.springsearch.anotation.SearchSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.msams.webrepo.ctr.abs.JsonApiRequestMapping;
import id.msams.webrepo.dao.dat.ref.Major;
import id.msams.webrepo.srv.abs.CrudService;
import id.msams.webrepo.srv.abs.SaveType;

@RestController
@JsonApiRequestMapping(path = "/majors")
public class MajorCtrl {

  @Autowired
  private CrudService<Long, Major> svc;

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<Major>>> findAll(
      @SearchSpec(searchParam = "search") Specification<Major> spec, Pageable page) {
    return svc.findAll(spec, page);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EntityModel<Major>> findAll(@PathVariable("id") Long id) {
    return svc.findById(id);
  }

  @PostMapping({ "", "/{id}" })
  public ResponseEntity<EntityModel<Major>> save(@Nullable @PathVariable("id") Long id,
      @RequestBody Major body) {
    return svc.save(id, body, SaveType.UPSERT);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<EntityModel<Major>> savePartial(@PathVariable("id") Long id,
      @RequestBody Major body) {
    return svc.save(id, body, SaveType.PARTIAL);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<RepresentationModel<?>> delete(@PathVariable("id") Long id) {
    return svc.delete(id);
  }

}
