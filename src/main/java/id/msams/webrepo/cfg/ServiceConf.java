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
import id.msams.webrepo.dto.dat.LecturerDto;
import id.msams.webrepo.dto.dat.StudentDto;
import id.msams.webrepo.dto.dat.ThesisDto;
import id.msams.webrepo.dto.dat.ref.FacultyDto;
import id.msams.webrepo.dto.dat.ref.MajorDto;
import id.msams.webrepo.dto.sec.RoleDto;
import id.msams.webrepo.dto.sec.UserDetailsDto;
import id.msams.webrepo.dto.sec.UserPrincipalDto;
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
  public CrudService<Long, Role, RoleDto> roleCrudService(RoleRepo repo,
      PagedResourcesAssembler<RoleDto> resourceAssembler) {
    return new CrudServiceImpl<>(mdlMap, selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, Faculty, FacultyDto> facultyCrudService(FacultyRepo repo,
      PagedResourcesAssembler<FacultyDto> resourceAssembler) {
    return new CrudServiceImpl<>(mdlMap, selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, Major, MajorDto> majorCrudService(MajorRepo repo,
      PagedResourcesAssembler<MajorDto> resourceAssembler) {
    return new CrudServiceImpl<>(mdlMap, selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, Thesis, ThesisDto> thesisCrudService(ThesisRepo repo,
      PagedResourcesAssembler<ThesisDto> resourceAssembler) {
    return new CrudServiceImpl<>(mdlMap, selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, UserPrincipal, UserPrincipalDto> userRepoCrudService(UserRepo repo,
      PagedResourcesAssembler<UserPrincipalDto> resourceAssembler) {
    return new CrudServiceImpl<>(mdlMap, selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, UserDetails, UserDetailsDto> userDetailsCrudService(UserDetailsRepo repo,
      PagedResourcesAssembler<UserDetailsDto> resourceAssembler) {
    return new CrudServiceImpl<>(mdlMap, selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, Student, StudentDto> studentCrudService(StudentRepo repo,
      PagedResourcesAssembler<StudentDto> resourceAssembler) {
    return new CrudServiceImpl<>(mdlMap, selMdlMap, repo, resourceAssembler);
  }

  @Bean
  public CrudService<Long, Lecturer, LecturerDto> lecturerCrudService(LecturerRepo repo,
      PagedResourcesAssembler<LecturerDto> resourceAssembler) {
    return new CrudServiceImpl<>(mdlMap, selMdlMap, repo, resourceAssembler);
  }

}
