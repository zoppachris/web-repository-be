package id.msams.webrepo.ctr.dat;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.msams.webrepo.dao.dat.Thesis;
import id.msams.webrepo.srv.abs.CrudService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/thesis")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ThesisCtrl {

  private final CrudService<Thesis, Long> svc;

  @GetMapping
  public ResponseEntity<Collection<Thesis>> findAll() {
    return svc.findAll();
  }

}
