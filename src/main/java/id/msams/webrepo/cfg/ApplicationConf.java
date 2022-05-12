package id.msams.webrepo.cfg;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import id.msams.webrepo.cfg.prp.SecurityProp;
import id.msams.webrepo.dao.sec.Role;
import id.msams.webrepo.dao.sec.RoleRepo;
import id.msams.webrepo.dao.sec.User;
import id.msams.webrepo.dao.sec.UserDetails;
import id.msams.webrepo.dao.sec.UserDetailsRepo;
import id.msams.webrepo.dao.sec.UserRepo;
import id.msams.webrepo.srv.AppUserDetailsSrvc;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableScheduling
@EnableJpaAuditing
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationConf {

  private final SecurityProp securityProp;

  private static final String SUPER_ADMIN_USERNAME = "superadmin";
  private static final String SUPER_ADMIN_NAME = "Super Admin";
  private static final String SUPER_ADMIN_ROLE_NAME = "SUPER_ADMIN";

  public Role initSystemRole(RoleRepo roleRepo) {
    Optional<Role> superAdmin = roleRepo.findOneByName(SUPER_ADMIN_ROLE_NAME);
    if (!superAdmin.isPresent()) {
      return roleRepo.save(Role.builder().name(SUPER_ADMIN_ROLE_NAME).build());
    } else {
      return superAdmin.get();
    }
  }

  public void initSystemUser(PasswordEncoder passwordEncoder, UserRepo userRepo,
      UserDetailsRepo userDetailsRepo, Role superAdminRole) {
    if (!userRepo.findOneByUsername(SUPER_ADMIN_USERNAME).isPresent()) {
      User superAdmin = User.builder()
          .username(SUPER_ADMIN_USERNAME)
          .password(passwordEncoder.encode("superadmin"))
          .active(true)
          .build();
      UserDetails superAdminDetails = UserDetails.builder()
          .name(SUPER_ADMIN_NAME)
          .user(superAdmin)
          .build();

      superAdmin.setUserDetails(superAdminDetails);
      superAdmin.setRoles(Stream.of(superAdminRole).collect(Collectors.toSet()));

      userRepo.save(superAdmin);
      userDetailsRepo.save(superAdminDetails);
    }
  }

  @Bean
  @Transactional
  public CommandLineRunner init(PasswordEncoder passwordEncoder, RoleRepo roleRepo, UserRepo userRepo,
      UserDetailsRepo userDetailsRepo) {
    return args -> {
      log.info("System initialization started");
      Role superAdminRole = initSystemRole(roleRepo);
      initSystemUser(passwordEncoder, userRepo, userDetailsRepo, superAdminRole);
      log.info("System initialization finished");
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(securityProp.getStrength());
  }

  @Bean
  public AppUserDetailsSrvc appUserDetailsSrvc(UserRepo userRepo) {
    return new AppUserDetailsSrvc(userRepo);
  }

}
