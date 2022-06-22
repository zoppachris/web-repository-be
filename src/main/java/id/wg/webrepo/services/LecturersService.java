package id.wg.webrepo.services;

import id.wg.webrepo.dtos.FacultiesDto;
import id.wg.webrepo.dtos.LecturersDto;
import id.wg.webrepo.dtos.PagingDto;
import id.wg.webrepo.dtos.UsersDto;
import id.wg.webrepo.models.Faculties;
import id.wg.webrepo.models.Lecturers;
import id.wg.webrepo.models.Users;
import id.wg.webrepo.repositories.LecturersRepository;
import id.wg.webrepo.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LecturersService {
    @Autowired
    private LecturersRepository repository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private FacultiesService facultiesService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<PagingDto> findAll(Pageable pageable, String search) {
        return Optional.of(repository.findAll(pageable, search))
                .map(pages -> PagingDto.builder()
                        .maxPage(pages.getTotalPages() == 0 ? 0 : pages.getTotalPages() - 1)
                        .page(pageable.getPageNumber())
                        .size(pageable.getPageSize())
                        .totalElements(pages.getTotalElements())
                        .content(toResponseDto(pages.getContent()))
                        .build());
    }

    public List<LecturersDto> toResponseDto(List<Lecturers> lecturers) {
        return lecturers.stream()
                .map(l -> LecturersDto.builder()
                        .lectureId(l.getLectureId())
                        .lectureName(l.getLectureName())
                        .nidn(l.getNidn())
                        .users(modelMapper.map(l.getUsers(), UsersDto.class))
                        .faculties(modelMapper.map(l.getFaculties(), FacultiesDto.class))
                        .build())
                .collect(Collectors.toList());
    }

    public long getTotalActive() {
        return repository.countActiveLecturers();
    }

    public Lecturers findById(Long id) {
        Optional<Lecturers> optional = repository.findById(id);
        Lecturers lecturers = new Lecturers();
        if (optional.isPresent()){
            lecturers = optional.get();
        }
        return lecturers;
    }

    public List<Lecturers> findByFaculties(Faculties faculties) {
        return repository.findByFaculties(faculties);
    }

    @Transactional
    public Lecturers save(LecturersDto dto, Long id) {
        Lecturers lecturers;
        if (id != null){
            lecturers = findById(id);
            Users user = lecturers.getUsers();
            user.setStatus(dto.isStatus());
            user.setUserName(dto.getNidn());
            user.setName(dto.getLectureName());
            if (!StringUtils.isEmpty(dto.getPassword())){
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }

            lecturers.setLectureName(dto.getLectureName());
            lecturers.setNidn(dto.getNidn());
            lecturers.setFaculties(facultiesService.findById(dto.getFaculties().getFacultyId()));
        } else{
            UsersDto usersDto = new UsersDto();
            usersDto.setUserName(dto.getNidn());
            usersDto.setName(dto.getLectureName());
            usersDto.setPassword(dto.getPassword());
            usersDto.setStatus(dto.isStatus());
            Users user = usersService.save(usersDto, null, Constants.LECTURER);

            lecturers = modelMapper.map(dto, Lecturers.class);
            lecturers.setUsers(user);
            repository.setSequence();
        }
        return repository.save(lecturers);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
