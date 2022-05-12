package id.msams.webrepo.dao.sec;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserPrincipal, Long> {
  @EntityGraph(attributePaths = "roles")
  Optional<UserPrincipal> findOneByUsername(String username);
}
