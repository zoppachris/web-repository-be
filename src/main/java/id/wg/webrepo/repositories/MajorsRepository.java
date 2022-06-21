package id.wg.webrepo.repositories;

import id.wg.webrepo.models.Faculties;
import id.wg.webrepo.models.Majors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorsRepository extends JpaRepository<Majors, Long> {
    @Query("SELECT m FROM Majors m " +
            "JOIN Faculties f ON m.faculties.facultyId = f.facultyId " +
            "WHERE (LOWER(m.majorName) LIKE LOWER(CONCAT('%',:search,'%')) " +
            "AND LOWER(f.facultyName) LIKE LOWER(CONCAT('%',:fakultas,'%')))")
    Page<Majors> findAll(Pageable pageable, String search, String fakultas);

    @Query("SELECT m FROM Majors m WHERE m.faculties = :faculties")
    List<Majors> findByFaculties(Faculties faculties);

    @Query(value = "SELECT setval('major_id_seq', (SELECT MAX(major_id) FROM majors), true)", nativeQuery = true)
    void setSequence();
}
