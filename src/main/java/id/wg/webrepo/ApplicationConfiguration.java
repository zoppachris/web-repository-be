package id.wg.webrepo;

import id.wg.webrepo.models.RoleUser;
import id.wg.webrepo.models.Roles;
import id.wg.webrepo.models.Users;
import id.wg.webrepo.repositories.RoleUserRepository;
import id.wg.webrepo.repositories.RolesRepository;
import id.wg.webrepo.repositories.UsersRepository;
import id.wg.webrepo.utils.Constants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
public class ApplicationConfiguration {

    private static final String SUPER_ADMIN_USERNAME = "superadmin";
    private static final String SUPER_ADMIN_NAME = "Super Admin";

    public Roles initSystemRole(RolesRepository rolesRepository) {
        Optional<Roles> admin = rolesRepository.findByName(Constants.ADMIN);
        if (!admin.isPresent()) {
            rolesRepository.save(Roles.builder().roleName(Constants.ADMIN).build());
        }
        Optional<Roles> lecturer = rolesRepository.findByName(Constants.LECTURER);
        if (!lecturer.isPresent()) {
            rolesRepository.save(Roles.builder().roleName(Constants.LECTURER).build());
        }
        Optional<Roles> student = rolesRepository.findByName(Constants.STUDENT);
        if (!student.isPresent()) {
            rolesRepository.save(Roles.builder().roleName(Constants.STUDENT).build());
        }

        Optional<Roles> superAdmin = rolesRepository.findByName(Constants.SUPERADMIN);
        if (!superAdmin.isPresent()) {
            return rolesRepository.save(Roles.builder().roleName(Constants.SUPERADMIN).build());
        } else {
            return superAdmin.get();
        }
    }

    public void initSystemUser(PasswordEncoder passwordEncoder, UsersRepository usersRepository,
                               RoleUserRepository roleUserRepository, Roles superAdminRole) {
        if (usersRepository.findByUsername(SUPER_ADMIN_USERNAME) == null) {
            Users superAdmin = Users.builder()
                    .name(SUPER_ADMIN_NAME)
                    .userName(SUPER_ADMIN_USERNAME)
                    .password(passwordEncoder.encode("superadmin"))
                    .status(true)
                    .build();

            RoleUser roleUser = RoleUser.builder()
                    .users(superAdmin)
                    .roles(superAdminRole)
                    .build();

            usersRepository.save(superAdmin);
            roleUserRepository.save(roleUser);
        }
    }

    @Bean
    @Transactional
    public CommandLineRunner init(PasswordEncoder passwordEncoder, UsersRepository usersRepository,
                                  RoleUserRepository roleUserRepository, RolesRepository rolesRepository) {
        return args -> {
            log.info("System initialization started");
            Roles superAdminRole = initSystemRole(rolesRepository);
            initSystemUser(passwordEncoder, usersRepository, roleUserRepository, superAdminRole);
            log.info("System initialization finished");
        };
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Web Theses Repository")
                        .description("Web Theses Repository Back-End Service")
                        .version("v1.0.0"))
                .components(
                        new Components()
                                .addSchemas("JwtClaims", new Schema<Map<String, Object>>()
                                        .addProperty("iss", new StringSchema().example("self")))
                )
                ;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
