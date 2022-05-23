package id.msams.webrepo.dao.sec;

import java.util.Optional;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

@Repository
@RepositoryRestResource(exported = false)
public interface RoleRepo extends JpaSpecificationRepository<Role, Long> {
  Optional<Role> findOneByName(RoleType name);
}
