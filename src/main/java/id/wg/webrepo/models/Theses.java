package id.wg.webrepo.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "theses", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Theses extends BaseModel {
    @Id
    @Column(name = "theses_id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "generator_theses_id_seq")
    @SequenceGenerator(name="generator_theses_id_seq", sequenceName="theses_id_seq", schema = "public", allocationSize = 1)
    public Long thesesId;

    @Column(name = "theses_title")
    public String thesesTitle;

    @Column(name = "abstracts")
    public String abstracts;

    @Column(name = "keywords")
    public String keywords;

    @Column(name = "partial_document_url")
    public String partialDocumentUrl;

    @Column(name = "full_document_url")
    public String fullDocumentUrl;

    @Column(name = "year")
    public String year;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Students students;
}
