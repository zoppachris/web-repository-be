package id.wg.webrepo.repositories;

import id.wg.webrepo.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query("SELECT r FROM Roles r WHERE LOWER(roleName) = LOWER(:name)")
    Optional<Roles> findByName(String name);
}
