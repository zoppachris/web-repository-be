package id.msams.webrepo.dao.abs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaSpecificationRepository<T, K> extends JpaRepository<T, K>, JpaSpecificationExecutor<T> {
}
