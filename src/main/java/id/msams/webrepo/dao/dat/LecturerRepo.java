package id.msams.webrepo.dao.dat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "lecturers", collectionResourceRel = "lecturers", itemResourceRel = "lecturer")
public interface LecturerRepo extends JpaRepository<Lecturer, Long> {
}
