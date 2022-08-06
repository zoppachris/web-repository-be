package id.wg.webrepo.repositories;

import id.wg.webrepo.models.Students;
import id.wg.webrepo.models.Theses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesesRepository extends JpaRepository<Theses, Long> {

    @Query("SELECT t FROM Theses t " +
            "JOIN Students s ON t.thesesId = s.theses.thesesId " +
            "JOIN Majors m ON s.majors.majorId = m.majorId " +
            "WHERE ((LOWER(t.thesesTitle) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "OR LOWER(t.abstracts) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "OR LOWER(t.keywords) LIKE LOWER(CONCAT('%',:search,'%'))) " +
            "AND LOWER(t.year) LIKE LOWER(CONCAT('%',:year,'%')) " +
            "AND LOWER(m.majorName) LIKE LOWER(CONCAT('%',:jurusan,'%')))")
    Page<Theses> findAll(Pageable pageable, String search, String year, String jurusan);

    @Query("SELECT t FROM Theses t " +
            "JOIN Students s ON t.thesesId = s.theses.thesesId " +
            "WHERE s = :students")
    Theses findByStudents(Students students);

    @Query(value = "SELECT setval('theses_id_seq', (SELECT MAX(theses_id) FROM theses), true)", nativeQuery = true)
    void setSequence();
}
