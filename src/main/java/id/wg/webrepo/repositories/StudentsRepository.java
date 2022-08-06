package id.wg.webrepo.repositories;

import id.wg.webrepo.models.Majors;
import id.wg.webrepo.models.Students;
import id.wg.webrepo.models.Theses;
import id.wg.webrepo.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<Students, Long> {
    @Query("SELECT s FROM Students s " +
            "JOIN Majors m ON s.majors.majorId = m.majorId " +
            "WHERE ((LOWER(s.studentName) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "OR LOWER(s.year) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "OR LOWER(s.nim) LIKE LOWER(CONCAT('%',:search,'%'))) " +
            "AND LOWER(m.majorName) LIKE LOWER(CONCAT('%',:jurusan,'%')))")
    Page<Students> findAll(Pageable pageable, String search, String jurusan);

    @Query("SELECT s FROM Students s " +
            "JOIN Majors m ON s.majors.majorId = m.majorId " +
            "WHERE s.theses.thesesId IS NULL AND ((LOWER(s.studentName) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "OR LOWER(s.year) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "OR LOWER(s.nim) LIKE LOWER(CONCAT('%',:search,'%'))) " +
            "AND LOWER(m.majorName) LIKE LOWER(CONCAT('%',:jurusan,'%')))")
    Page<Students> findHasNotTheses(Pageable pageable, String search, String jurusan);

    @Query("SELECT s FROM Students s " +
            "JOIN Majors m ON s.majors.majorId = m.majorId " +
            "WHERE s.theses.thesesId IS NOT NULL AND ((LOWER(s.studentName) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "OR LOWER(s.year) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "OR LOWER(s.nim) LIKE LOWER(CONCAT('%',:search,'%'))) " +
            "AND LOWER(m.majorName) LIKE LOWER(CONCAT('%',:jurusan,'%')))")
    Page<Students> findHasTheses(Pageable pageable, String search, String jurusan);

    @Query("SELECT s FROM Students s WHERE s.majors = :majors")
    List<Students> findByMajors(Majors majors);

    @Query("SELECT s FROM Students s WHERE s.users = :users")
    Optional<Students> findByUsers(Users users);

    @Query("SELECT s FROM Students s WHERE s.theses = :theses")
    Students findByTheses(Theses theses);

    @Query("SELECT COUNT(s) FROM Students s " +
            "JOIN Users u ON s.users.userId = u.userId " +
            "WHERE u.status = true")
    long countActiveStudents();

    @Query(value = "SELECT setval('student_id_seq', (SELECT MAX(student_id) FROM students), true)", nativeQuery = true)
    void setSequence();
}
