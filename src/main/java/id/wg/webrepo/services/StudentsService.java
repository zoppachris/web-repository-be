package id.wg.webrepo.services;

import id.wg.webrepo.dtos.MajorsDto;
import id.wg.webrepo.dtos.PagingDto;
import id.wg.webrepo.dtos.StudentsDto;
import id.wg.webrepo.dtos.UsersDto;
import id.wg.webrepo.models.Majors;
import id.wg.webrepo.models.Students;
import id.wg.webrepo.models.Users;
import id.wg.webrepo.repositories.StudentsRepository;
import id.wg.webrepo.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StudentsService {
    @Autowired
    private StudentsRepository repository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private MajorsService majorsService;

    @Autowired
    private ThesesService thesesService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<PagingDto> findAll(Pageable pageable, String search, String jurusan) {
        return Optional.of(repository.findAll(pageable, search, jurusan))
                .map(pages -> PagingDto.builder()
                        .maxPage(pages.getTotalPages() == 0 ? 0 : pages.getTotalPages() - 1)
                        .page(pageable.getPageNumber())
                        .size(pageable.getPageSize())
                        .totalElements(pages.getTotalElements())
                        .content(toResponseDto(pages.getContent()))
                        .build());
    }

    public List<StudentsDto> toResponseDto(List<Students> students) {
        return students.stream()
                .map(s -> StudentsDto.builder()
                        .studentId(s.getStudentId())
                        .studentName(s.getStudentName())
                        .nim(s.getNim())
                        .year(s.getYear())
                        .majors(modelMapper.map(s.getMajors(), MajorsDto.class))
                        .build())
                .collect(Collectors.toList());
    }

    public long getTotalActive() {
        return repository.countActiveStudents();
    }

    public Students findById(Long id) {
        Optional<Students> optional = repository.findById(id);
        Students students = new Students();
        if (optional.isPresent()){
            students = optional.get();
        }
        return students;
    }

    public List<Students> findByMajors(Majors majors) {
        return repository.findByMajors(majors);
    }

    @Transactional
    public Students save(StudentsDto dto, Long id) {
        Students students;
        if (id != null){
            students = findById(id);
            Users user = students.getUsers();
            user.setStatus(dto.isStatus());
            user.setUserName(dto.getNim());
            user.setName(dto.getStudentName());
            if (!StringUtils.isEmpty(dto.getPassword())){
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }

            students.setStudentName(dto.getStudentName());
            students.setNim(dto.getNim());
            students.setYear(dto.getYear());
            students.setMajors(majorsService.findById(dto.getMajors().getMajorId()));
        } else{
            UsersDto usersDto = new UsersDto();
            usersDto.setUserName(dto.getNim());
            usersDto.setName(dto.getStudentName());
            usersDto.setPassword(dto.getPassword());
            usersDto.setStatus(dto.isStatus());
            Users user = usersService.save(usersDto, null, Constants.STUDENT);

            students = modelMapper.map(dto, Students.class);
            students.setUsers(user);
            repository.setSequence();
        }
        return repository.save(students);
    }

    @Transactional
    public boolean delete(Long id) {
        Students students = findById(id);
        if (CollectionUtils.isEmpty(thesesService.findByStudents(students))){
            usersService.delete(students.getUsers().getUserId());
            return true;
        }else{
            return false;
        }
    }
}
