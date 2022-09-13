package id.wg.webrepo.repositories;

import id.wg.webrepo.models.Faculties;
import id.wg.webrepo.models.Lecturers;
import id.wg.webrepo.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturersRepository extends JpaRepository<Lecturers, Long> {
    @Query("SELECT l FROM Lecturers l " +
            "LEFT JOIN Faculties f ON l.faculties.facultyId = f.facultyId " +
            "WHERE ((LOWER(l.lectureName) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "OR LOWER(l.nidn) LIKE LOWER(CONCAT('%',:search,'%'))) " +
            "AND LOWER(f.facultyName) LIKE LOWER(CONCAT('%',:fakultas,'%')))")
    Page<Lecturers> findAll(Pageable pageable, String search, String fakultas);

    @Query("SELECT l FROM Lecturers l WHERE l.faculties = :faculties")
    List<Lecturers> findByFaculties(Faculties faculties);

    @Query("SELECT l FROM Lecturers l WHERE l.users = :users")
    Optional<Lecturers> findByUsers(Users users);

    @Query("SELECT COUNT(l) FROM Lecturers l " +
            "JOIN Users u ON l.users.userId = u.userId " +
            "WHERE u.status = true")
    long countActiveLecturers();

    @Query(value = "SELECT setval('lecture_id_seq', (SELECT MAX(lecture_id) FROM lecturers), true)", nativeQuery = true)
    void setSequence();
}
