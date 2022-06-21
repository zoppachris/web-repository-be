package id.wg.webrepo.repositories;

import id.wg.webrepo.models.RoleUser;
import id.wg.webrepo.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
    @Query("SELECT ru FROM RoleUser ru " +
            "WHERE users = :users ")
    RoleUser findByUsers(Users users);

    @Query(value = "SELECT setval('role_user_id_seq', (SELECT MAX(role_user_id) FROM role_user), true)", nativeQuery = true)
    void setSequence();
}
