package id.msams.webrepo.ctr;

import com.sipios.springsearch.anotation.SearchSpec;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.msams.webrepo.ctr.abs.JsonApiRequestMapping;
import id.msams.webrepo.dao.dat.Lecturer;
import id.msams.webrepo.dao.dat.Student;
import id.msams.webrepo.dao.dat.ref.Faculty;
import id.msams.webrepo.dao.sec.UserDetails;
import id.msams.webrepo.dao.sec.UserPrincipal;
import id.msams.webrepo.dto.dat.UserRegistrationReq;
import id.msams.webrepo.ext.i18n.utility.MessageUtil;
import id.msams.webrepo.srv.abs.CrudService;
import id.msams.webrepo.srv.abs.EntityNotFoundException;
import id.msams.webrepo.srv.abs.SaveType;

@RestController
@JsonApiRequestMapping(path = "/users")
public class UserCtrl {

  @Autowired
  private CrudService<Long, UserPrincipal> svc;

  @Autowired
  private PasswordEncoder passEnc;
  @Autowired
  @Qualifier("modelMapper")
  private ModelMapper mdlMap;

  @Autowired
  private MessageUtil msg;

  @GetMapping
  public ResponseEntity<?> findAll(@SearchSpec(searchParam = "search") Specification<UserPrincipal> spec,
      Pageable paging) {
    return svc.findAll(spec, paging);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable("id") Long id) {
    return svc.findById(id);
  }

  @PostMapping("/register/{type}")
  public ResponseEntity<?> save(@PathVariable("type") String type, @RequestBody UserRegistrationReq body) {
    UserPrincipal newUser = mdlMap.map(body, UserPrincipal.class);
    newUser.setPassword(passEnc.encode(newUser.getPassword()));
    newUser.setActive(true);

    UserDetails newUserDetails = mdlMap.map(body, UserDetails.class);
    newUser.setUserDetails(newUserDetails);
    newUserDetails.setUser(newUser);

    switch (type) {
      case "admin":
        break;
      case "lecturer":
        Lecturer newLecturer = Lecturer.builder()
            .nidn(newUser.getUsername())
            .build();
        newUser.setLecturer(newLecturer);
        newLecturer.setUser(newUser);
        newLecturer.setFaculty(mdlMap.map(body.getEtc().get("faculty"), Faculty.class));
        break;
      case "student":
        Student newStudent = Student.builder()
            .nim(newUser.getUsername())
            .year(Integer.parseInt(body.getEtc().get("year").toString()))
            .build();
        newUser.setStudent(newStudent);
        newStudent.setUser(newUser);
        break;
      default:
        throw new UnsupportedOperationException(msg.get("SYSTEM.ERROR.OPERATION.NOT-SUPPORTED.DETAIL", type));
    }
    return svc.save(null, newUser, SaveType.INSERT);
  }

  @PutMapping("/{action}/{id}")
  public ResponseEntity<?> toggleActivity(@PathVariable("action") String action, @PathVariable("id") Long id) {
    UserPrincipal user = svc.repo().findById(id)
        .orElseThrow(() -> new EntityNotFoundException(msg.get("SYSTEM.ERROR.ENTITY.NOT-FOUND.DETAIL", id)));
    switch (action) {
      case "reactivate":
        if (!user.getActive().booleanValue()) {
          user.setActive(true);
        } else {
          throw new UnsupportedOperationException(msg.get("SYSTEM.ERROR.USER.ALREADY-ACTIVE.DETAIL"));
        }
        break;
      case "deactivate":
        if (user.getActive().booleanValue()) {
          user.setActive(false);
        } else {
          throw new UnsupportedOperationException(msg.get("SYSTEM.ERROR.USER.ALREADY-INACTIVE.DETAIL"));
        }
        break;
      default:
        throw new UnsupportedOperationException(msg.get("SYSTEM.ERROR.OPERATION.NOT-SUPPORTED.DETAIL", action));
    }
    return svc.save(id, user, SaveType.UPDATE);
  }

}
