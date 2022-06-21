package id.wg.webrepo.repositories;

import id.wg.webrepo.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u " +
            "WHERE (LOWER(u.name) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "OR LOWER(u.userName) LIKE LOWER(CONCAT('%',:search,'%')))")
    Page<Users> findAll(Pageable pageable, String search);

    @Query("SELECT u FROM Users u " +
            "WHERE userName = :userName ")
    Users findByUsername(String userName);

    @Query(value = "SELECT setval('user_id_seq', (SELECT MAX(user_id) FROM users), true)", nativeQuery = true)
    void setSequence();
}
