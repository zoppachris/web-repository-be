package id.msams.webrepo.dao.sec;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface UserDetailsRepo extends JpaRepository<UserDetails, Long> {
}
