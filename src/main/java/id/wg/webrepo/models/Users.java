package id.wg.webrepo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseModel {

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "generator_user_id_seq")
    @SequenceGenerator(name="generator_user_id_seq", sequenceName="user_id_seq", schema = "public", allocationSize = 1)
    public Long userId;

    @Column(name = "user_name", nullable = false, unique = true)
    public String userName;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "password", nullable = false)
    public String password;

    @Column(name = "status", nullable = false)
    public boolean status;
}
