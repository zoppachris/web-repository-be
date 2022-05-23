package id.msams.webrepo.dao.dat.ref;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "majors", collectionResourceRel = "majors", itemResourceRel = "major")
public interface MajorRepo extends JpaRepository<Major, Long> {
}
