package id.wg.webrepo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Roles extends BaseModel {
    @Id
    @Column(name = "role_id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "generator_role_id_seq")
    @SequenceGenerator(name="generator_role_id_seq", sequenceName="role_id_seq", schema = "public", allocationSize = 1)
    public Long roleId;

    @Column(name = "role_name", nullable = false)
    public String roleName;
}
