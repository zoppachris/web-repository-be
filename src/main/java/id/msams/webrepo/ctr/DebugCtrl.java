package id.msams.webrepo.ctr;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
public class DebugCtrl {

  @GetMapping
  public Object get() {
    return ResponseEntity.ok(true);
  }

}
