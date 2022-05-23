package id.msams.webrepo.dao.dat;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

@Repository
@RepositoryRestResource(path = "students", collectionResourceRel = "students", itemResourceRel = "student")
public interface StudentRepo extends JpaSpecificationRepository<Student, Long> {
}
