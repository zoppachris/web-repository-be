package id.wg.webrepo.repositories;

import id.wg.webrepo.models.Faculties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultiesRepository extends JpaRepository<Faculties, Long> {
    @Query("SELECT f FROM Faculties f " +
            "WHERE (LOWER(f.facultyName) LIKE LOWER(CONCAT('%',:search,'%')))")
    Page<Faculties> findAll(Pageable pageable, String search);

    @Query(value = "SELECT setval('faculty_id_seq', (SELECT MAX(faculty_id) FROM faculties), true)", nativeQuery = true)
    void setSequence();
}
