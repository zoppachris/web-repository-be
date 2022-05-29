package id.msams.webrepo.dao.sec;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

@Repository
@RepositoryRestResource(exported = false)
public interface UserDetailsRepo extends JpaSpecificationRepository<UserDetails, Long> {
}
