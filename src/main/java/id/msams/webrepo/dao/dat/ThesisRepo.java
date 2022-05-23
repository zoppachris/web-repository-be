package id.msams.webrepo.dao.dat;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import id.msams.webrepo.dao.abs.JpaSpecificationRepository;

@Repository
@RepositoryRestResource(path = "theses", collectionResourceRel = "theses", itemResourceRel = "thesis")
public interface ThesisRepo extends JpaSpecificationRepository<Thesis, Long> {
}
