package id.msams.webrepo.cfg;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;

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

  @Bean
  public CrudService<Long, Role> roleCrudService(RoleRepo repo,
      PagedResourcesAssembler<Role> resourceAssembler) {
    return new CrudServiceImpl<>(selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, Faculty> facultyCrudService(FacultyRepo repo,
      PagedResourcesAssembler<Faculty> resourceAssembler) {
    return new CrudServiceImpl<>(selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, Major> majorCrudService(MajorRepo repo,
      PagedResourcesAssembler<Major> resourceAssembler) {
    return new CrudServiceImpl<>(selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, Thesis> thesisCrudService(ThesisRepo repo,
      PagedResourcesAssembler<Thesis> resourceAssembler) {
    return new CrudServiceImpl<>(selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, UserPrincipal> userRepoCrudService(UserRepo repo,
      PagedResourcesAssembler<UserPrincipal> resourceAssembler) {
    return new CrudServiceImpl<>(selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, UserDetails> userDetailsCrudService(UserDetailsRepo repo,
      PagedResourcesAssembler<UserDetails> resourceAssembler) {
    return new CrudServiceImpl<>(selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, Student> studentCrudService(StudentRepo repo,
      PagedResourcesAssembler<Student> resourceAssembler) {
    return new CrudServiceImpl<>(selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, Lecturer> lecturerCrudService(LecturerRepo repo,
      PagedResourcesAssembler<Lecturer> resourceAssembler) {
    return new CrudServiceImpl<>(selMdlMap, repo, resourceAssembler);
  }

}
