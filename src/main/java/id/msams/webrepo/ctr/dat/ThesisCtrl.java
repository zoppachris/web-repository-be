package id.msams.webrepo.ctr.dat;

import com.sipios.springsearch.anotation.SearchSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import id.msams.webrepo.dao.dat.Thesis;
import id.msams.webrepo.srv.abs.CrudService;
import id.msams.webrepo.srv.abs.SaveType;
import lombok.RequiredArgsConstructor;

@RestController
@JsonApiRequestMapping(path = "/theses")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ThesisCtrl {

  private final CrudService<Long, Thesis> svc;

  @GetMapping
  public ResponseEntity<?> findAll(
      @SearchSpec(searchParam = "search") Specification<Thesis> spec, Pageable paging) {
    return svc.findAll(spec, paging);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable("id") Long id) {
    return svc.findById(id);
  }

  @PostMapping({ "", "/{id}" })
  public ResponseEntity<?> save(@Nullable @PathVariable("id") Long id,
      @RequestBody Thesis body) {
    return svc.save(id, body, SaveType.UPSERT);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> savePartial(@PathVariable("id") Long id,
      @RequestBody Thesis body) {
    return svc.save(id, body, SaveType.PARTIAL);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    return svc.delete(id);
  }

}
