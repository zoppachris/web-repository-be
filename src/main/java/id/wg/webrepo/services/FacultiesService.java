package id.wg.webrepo.services;

import id.wg.webrepo.dtos.FacultiesDto;
import id.wg.webrepo.dtos.PagingDto;
import id.wg.webrepo.models.Faculties;
import id.wg.webrepo.repositories.FacultiesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FacultiesService {
    @Autowired
    private FacultiesRepository repository;

    @Autowired
    private MajorsService majorsService;

    @Autowired
    private LecturersService lecturersService;

    @Autowired
    private ModelMapper modelMapper;

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

    public List<FacultiesDto> toResponseDto(List<Faculties> faculties) {
        return faculties.stream()
                .map(f -> FacultiesDto.builder()
                        .facultyId(f.getFacultyId())
                        .facultyName(f.getFacultyName())
                        .build())
                .collect(Collectors.toList());
    }

    public Faculties findById(Long id) {
        Optional<Faculties> optional = repository.findById(id);
        Faculties faculties = new Faculties();
        if (optional.isPresent()){
            faculties = optional.get();
        }
        return faculties;
    }

    @Transactional
    public Faculties save(FacultiesDto dto, Long id) {
        Faculties faculties;
        if (id != null){
            faculties = repository.findById(id).get();
            faculties.setFacultyName(dto.getFacultyName());
        } else{
            faculties = modelMapper.map(dto, Faculties.class);
            repository.setSequence();
        }
        return repository.save(faculties);
    }

    @Transactional
    public boolean delete(Long id) {
        Faculties faculties = repository.findById(id).get();
        if (CollectionUtils.isEmpty(majorsService.findByFaculties(faculties)) &&
                CollectionUtils.isEmpty(lecturersService.findByFaculties(faculties))){
            repository.delete(faculties);
            return true;
        }else{
            return false;
        }
    }
}
