package id.wg.webrepo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "lecturers", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lecturers extends BaseModel {
    @Id
    @Column(name = "lecture_id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "generator_lecture_id_seq")
    @SequenceGenerator(name="generator_lecture_id_seq", sequenceName="lecture_id_seq", schema = "public", allocationSize = 1)
    public Long lectureId;

    @Column(name = "lecture_name", nullable = false)
    public String lectureName;

    @Column(name = "nidn", nullable = false, unique = true)
    public String nidn;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculties faculties;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private Users users;
}
