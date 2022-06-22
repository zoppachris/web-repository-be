package id.wg.webrepo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "role_user", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleUser extends BaseModel {
    @Id
    @Column(name = "role_user_id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "generator_role_user_id_seq")
    @SequenceGenerator(name="generator_role_user_id_seq", sequenceName="role_user_id_seq", schema = "public", allocationSize = 1)
    public Long roleUserId;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;
}
