package id.msams.webrepo.cfg;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.toedter.spring.hateoas.jsonapi.JsonApiConfiguration;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
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
import id.msams.webrepo.dao.sec.RoleType;
import id.msams.webrepo.dao.sec.UserPrincipal;
import id.msams.webrepo.dao.sec.UserDetails;
import id.msams.webrepo.dao.sec.UserDetailsRepo;
import id.msams.webrepo.dao.sec.UserRepo;
import id.msams.webrepo.srv.AppUserDetailsSrvc;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
@EnableJpaAuditing
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationConf {

  private final SecurityProp securityProp;

  private static final String SUPER_ADMIN_USERNAME = "superadmin";
  private static final String SUPER_ADMIN_NAME = "Super Admin";

  public Role initSystemRole(RoleRepo roleRepo) {
    Optional<Role> superAdmin = roleRepo.findOneByName(RoleType.SUPER_ADMIN);
    if (!superAdmin.isPresent()) {
      return roleRepo.save(Role.builder().name(RoleType.SUPER_ADMIN).build());
    } else {
      return superAdmin.get();
    }
  }

  public void initSystemUser(PasswordEncoder passwordEncoder, UserRepo userRepo,
      UserDetailsRepo userDetailsRepo, Role superAdminRole) {
    if (!userRepo.findOneByUsername(SUPER_ADMIN_USERNAME).isPresent()) {
      UserPrincipal superAdmin = UserPrincipal.builder()
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

  @Bean
  public OpenAPI openApi() {
    // @formatter:off
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
    // @formatter:on
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objMap = new ObjectMapper();
    objMap.enable(
        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
        SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
    objMap.enable(
        DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
    return objMap;
  }

  @Bean
  public JsonApiConfiguration jsonApiConfiguration() {
    return new JsonApiConfiguration()
        .withObjectMapperCustomizer(objMap -> {
          objMap.enable(
              SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
              SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
          objMap.enable(
              DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
          objMap.registerModule(new JavaTimeModule());
        });
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public ModelMapper selectiveModelMapper() {
    ModelMapper mdlMap = new ModelMapper();
    mdlMap.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    return mdlMap;
  }

}
