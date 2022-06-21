package id.wg.webrepo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "faculties", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Faculties extends BaseModel {

    @Id
    @Column(name = "faculty_id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "generator_faculty_id_seq")
    @SequenceGenerator(name="generator_faculty_id_seq", sequenceName="faculty_id_seq", schema = "public", allocationSize = 1)
    public Long facultyId;

    @Column(name = "faculty_name", nullable = false)
    public String facultyName;
}
