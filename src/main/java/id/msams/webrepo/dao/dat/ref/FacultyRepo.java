package id.msams.webrepo.dao.dat.ref;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "faculties", collectionResourceRel = "faculties", itemResourceRel = "faculty")
public interface FacultyRepo extends JpaRepository<Faculty, Long> {
}
