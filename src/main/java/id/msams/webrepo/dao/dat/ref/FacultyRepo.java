package id.msams.webrepo.dao.dat.ref;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

@Repository
@RepositoryRestResource(path = "faculties", collectionResourceRel = "faculties", itemResourceRel = "faculty")
public interface FacultyRepo extends JpaSpecificationRepository<Faculty, Long> {
}
