package id.msams.webrepo.srv.abs;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CrudServiceImpl<T, K> implements CrudService<T, K> {

  private final JpaRepository<T, K> repo;

  @Override
  public ResponseEntity<Collection<T>> findAll() {
    return ResponseEntity.ok(repo.findAll());
  }

}
