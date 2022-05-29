package id.msams.webrepo.cfg;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import id.msams.webrepo.dao.dat.Lecturer;
import id.msams.webrepo.dao.dat.LecturerRepo;
import id.msams.webrepo.dao.dat.Student;
import id.msams.webrepo.dao.dat.StudentRepo;
import id.msams.webrepo.dao.dat.Thesis;
import id.msams.webrepo.dao.dat.ThesisRepo;
import id.msams.webrepo.dao.dat.ref.Faculty;
import id.msams.webrepo.dao.dat.ref.FacultyRepo;
import id.msams.webrepo.dao.dat.ref.Major;
import id.msams.webrepo.dao.dat.ref.MajorRepo;
import id.msams.webrepo.dao.sec.Role;
import id.msams.webrepo.dao.sec.RoleRepo;
import id.msams.webrepo.dao.sec.UserDetails;
import id.msams.webrepo.dao.sec.UserDetailsRepo;
import id.msams.webrepo.dao.sec.UserPrincipal;
import id.msams.webrepo.dao.sec.UserRepo;
import id.msams.webrepo.ext.i18n.utility.MessageUtil;
import id.msams.webrepo.srv.abs.CrudService;
import id.msams.webrepo.srv.abs.CrudServiceImpl;

@Configuration
public class ServiceConf {

  @Autowired
  @Qualifier("modelMapper")
  private ModelMapper mdlMap;

  @Autowired
  @Qualifier("selectiveModelMapper")
  private ModelMapper selMdlMap;

  @Autowired
  private MessageUtil msg;

  @Bean
  public CrudService<Long, Role> roleCrudService(RoleRepo repo) {
    return new CrudServiceImpl<>(selMdlMap, repo, msg);
  }

  @Bean
  public CrudService<Long, Faculty> facultyCrudService(FacultyRepo repo) {
    return new CrudServiceImpl<>(selMdlMap, repo, msg);
  }

  @Bean
  public CrudService<Long, Major> majorCrudService(MajorRepo repo) {
    return new CrudServiceImpl<>(selMdlMap, repo, msg);
  }

  @Bean
  public CrudService<Long, Thesis> thesisCrudService(ThesisRepo repo) {
    return new CrudServiceImpl<>(selMdlMap, repo, msg);
  }

  @Bean
  public CrudService<Long, UserPrincipal> userCrudService(UserRepo repo) {
    return new CrudServiceImpl<>(selMdlMap, repo, msg);
  }

  @Bean
  public CrudService<Long, UserDetails> userDetailsCrudService(UserDetailsRepo repo) {
    return new CrudServiceImpl<>(selMdlMap, repo, msg);
  }

  @Bean
  public CrudService<Long, Student> studentCrudService(StudentRepo repo) {
    return new CrudServiceImpl<>(selMdlMap, repo, msg);
  }

  @Bean
  public CrudService<Long, Lecturer> lecturerCrudService(LecturerRepo repo) {
    return new CrudServiceImpl<>(selMdlMap, repo, msg);
  }

}
