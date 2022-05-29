package id.msams.webrepo.dao.dat.ref;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

@Repository
@RepositoryRestResource(path = "majors", collectionResourceRel = "majors", itemResourceRel = "major")
public interface MajorRepo extends JpaSpecificationRepository<Major, Long> {
}
