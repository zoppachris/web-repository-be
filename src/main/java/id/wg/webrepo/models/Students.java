package id.wg.webrepo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "students", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Students extends BaseModel {
    @Id
    @Column(name = "student_id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "generator_student_id_seq")
    @SequenceGenerator(name="generator_student_id_seq", sequenceName="student_id_seq", schema = "public", allocationSize = 1)
    public Long studentId;

    @Column(name = "student_name", nullable = false)
    public String studentName;

    @Column(name = "nim", nullable = false, unique = true)
    public String nim;

    @Column(name = "year", nullable = false)
    public String year;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Majors majors;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToOne
    @JoinColumn(name = "theses_id")
    private Theses theses;
}
