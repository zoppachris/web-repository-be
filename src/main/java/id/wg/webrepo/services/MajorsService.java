package id.wg.webrepo.services;

import id.wg.webrepo.dtos.FacultiesDto;
import id.wg.webrepo.dtos.MajorsDto;
import id.wg.webrepo.dtos.PagingDto;
import id.wg.webrepo.models.Faculties;
import id.wg.webrepo.models.Majors;
import id.wg.webrepo.repositories.MajorsRepository;
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
public class MajorsService {
    @Autowired
    private MajorsRepository repository;

    @Autowired
    private FacultiesService facultiesService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<PagingDto> findAll(Pageable pageable, String search, String fakultas) {
        return Optional.of(repository.findAll(pageable, search, fakultas))
                .map(pages -> PagingDto.builder()
                        .maxPage(pages.getTotalPages() == 0 ? 0 : pages.getTotalPages() - 1)
                        .page(pageable.getPageNumber())
                        .size(pageable.getPageSize())
                        .totalElements(pages.getTotalElements())
                        .content(toResponseDto(pages.getContent()))
                        .build());
    }

    public List<MajorsDto> toResponseDto(List<Majors> majors) {
        return majors.stream()
                .map(m -> MajorsDto.builder()
                        .majorId(m.getMajorId())
                        .majorName(m.getMajorName())
                        .faculties(modelMapper.map(m.getFaculties(), FacultiesDto.class))
                        .build())
                .collect(Collectors.toList());
    }

    public List<MajorsDto> getLov() {
        return repository.findAll().stream()
                .map(m -> MajorsDto.builder()
                        .majorId(m.getMajorId())
                        .majorName(m.getMajorName())
                        .build())
                .collect(Collectors.toList());
    }

    public Majors findById(Long id) {
        Optional<Majors> optional = repository.findById(id);
        Majors majors = new Majors();
        if (optional.isPresent()){
            majors = optional.get();
        }
        return majors;
    }

    public List<Majors> findByFaculties(Faculties faculties) {
        return repository.findByFaculties(faculties);
    }

    @Transactional
    public Majors save(MajorsDto dto, Long id) {
        Majors majors;
        if (id != null){
            majors = repository.findById(id).get();
            majors.setMajorName(dto.getMajorName());
            majors.setFaculties(facultiesService.findById(dto.getFaculties().getFacultyId()));
        } else{
            majors = modelMapper.map(dto, Majors.class);
            repository.setSequence();
        }
        return repository.save(majors);
    }

    @Transactional
    public boolean delete(Long id) {
        Majors majors = repository.findById(id).get();
        if (CollectionUtils.isEmpty(studentsService.findByMajors(majors))){
            repository.delete(majors);
            return true;
        }else{
            return false;
        }
    }
}
