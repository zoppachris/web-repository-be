package id.msams.webrepo.dao.dat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "theses", collectionResourceRel = "theses", itemResourceRel = "thesis")
public interface ThesisRepo extends JpaRepository<Thesis, Long> {
}
