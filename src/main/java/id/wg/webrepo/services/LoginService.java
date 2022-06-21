package id.wg.webrepo.services;

import id.wg.webrepo.dtos.ChangePasswordDto;
import id.wg.webrepo.dtos.LoginDto;
import id.wg.webrepo.models.Lecturers;
import id.wg.webrepo.models.RoleUser;
import id.wg.webrepo.models.Students;
import id.wg.webrepo.models.Users;
import id.wg.webrepo.repositories.LecturersRepository;
import id.wg.webrepo.repositories.RoleUserRepository;
import id.wg.webrepo.repositories.StudentsRepository;
import id.wg.webrepo.repositories.UsersRepository;
import id.wg.webrepo.security.JwtTokenUtil;
import id.wg.webrepo.security.JwtUserDetailsService;
import id.wg.webrepo.security.UserSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LecturersRepository lecturersRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public LoginDto login(LoginDto dto) throws Exception {
        authenticate(dto.getUsername(), dto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        Users users = usersRepository.findByUsername(dto.getUsername());
        RoleUser roleUser = roleUserRepository.findByUsers(users);
        Optional<Lecturers> lecturers = lecturersRepository.findByUsers(users);
        Optional<Students> students = studentsRepository.findByUsers(users);

        LoginDto response = new LoginDto();
        response.setToken(token);
        response.setType("Bearer");
        response.setExpire(jwtTokenUtil.getExpirationDateFromToken(token).toInstant().getEpochSecond());
        response.setUserId(users.getUserId());
        response.setName(users.getName());
        response.setUsername(dto.getUsername());
        response.setRoleName(roleUser.getRole().getRoleName());
        lecturers.ifPresent(value -> response.setFacultyName(value.getFaculties().getFacultyName()));
        if (students.isPresent()){
            response.setMajorName(students.get().getMajors().getMajorName());
            response.setFacultyName(students.get().getMajors().getFaculties().getFacultyName());
        }
        return response;
    }

    @Transactional
    public String changePassword(ChangePasswordDto dto, boolean isSuperadmin) throws Exception {
        if (isSuperadmin) {
            Users superadmin = usersRepository.findById(1L).get();
            superadmin.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            return "Change password Super Admin is success";
        }

        authenticate(UserSessionUtil.getUsername(), dto.getOldPassword());
        Users users = usersRepository.findByUsername(UserSessionUtil.getUsername());
        users.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        usersRepository.save(users);
        return "Change password is success";
    }
}
