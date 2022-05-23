package id.msams.webrepo.dao.dat;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

@Repository
@RepositoryRestResource(path = "lecturers", collectionResourceRel = "lecturers", itemResourceRel = "lecturer")
public interface LecturerRepo extends JpaSpecificationRepository<Lecturer, Long> {
}
