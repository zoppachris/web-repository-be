package id.wg.webrepo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "majors", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Majors extends BaseModel {
    @Id
    @Column(name = "major_id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "generator_major_id_seq")
    @SequenceGenerator(name="generator_major_id_seq", sequenceName="major_id_seq", schema = "public", allocationSize = 1)
    public Long majorId;

    @Column(name = "major_name", nullable = false)
    public String majorName;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculties faculties;
}
