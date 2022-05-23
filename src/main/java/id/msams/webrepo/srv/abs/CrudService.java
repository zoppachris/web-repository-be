package id.msams.webrepo.srv.abs;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

public interface CrudService<T, K> {

  ResponseEntity<Collection<T>> findAll();

}
