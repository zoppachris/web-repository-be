package id.wg.webrepo.services;

import id.wg.webrepo.dtos.PagingDto;
import id.wg.webrepo.dtos.StudentsDto;
import id.wg.webrepo.dtos.ThesesDto;
import id.wg.webrepo.models.Students;
import id.wg.webrepo.models.Theses;
import id.wg.webrepo.repositories.ThesesRepository;
import id.wg.webrepo.security.UserSessionUtil;
import id.wg.webrepo.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ThesesService {
    @Autowired
    private ThesesRepository repository;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MinioService minioService;

    public Optional<PagingDto> findAll(Pageable pageable, String search, String year, String jurusan) {
        return Optional.of(repository.findAll(pageable, search, year, jurusan))
                .map(pages -> PagingDto.builder()
                        .maxPage(pages.getTotalPages() == 0 ? 0 : pages.getTotalPages() - 1)
                        .page(pageable.getPageNumber())
                        .size(pageable.getPageSize())
                        .totalElements(pages.getTotalElements())
                        .content(toResponseDto(pages.getContent()))
                        .build());
    }

    public List<ThesesDto> toResponseDto(List<Theses> theses) {
        return theses.stream()
                .map(t -> ThesesDto.builder()
                        .thesesId(t.getThesesId())
                        .thesesTitle(t.getThesesTitle())
                        .abstracts(t.getAbstracts())
                        .keywords(t.getKeywords())
                        .year(t.getYear())
                        .documentUrl(getUrlDocument(t))
                        .students(modelMapper.map(t.getStudents(), StudentsDto.class))
                        .build())
                .collect(Collectors.toList());
    }

    public int getTotalActive() {
        return repository.findAll().size();
    }

    public ThesesDto findById(Long id) {
        Optional<Theses> optional = repository.findById(id);
        Theses theses = new Theses();
        if (optional.isPresent()){
            theses = optional.get();
        }
        ThesesDto dto = modelMapper.map(theses, ThesesDto.class);
        dto.setDocumentUrl(getUrlDocument(theses));
        return dto;
    }

    @Transactional
    public Theses save(ThesesDto dto, Long id) {
        Theses theses;
        if (id != null){
            theses = repository.findById(id).get();
            theses.setThesesTitle(dto.getThesesTitle());
            theses.setAbstracts(dto.getAbstracts());
            theses.setKeywords(dto.getKeywords());
            theses.setYear(dto.getYear());
            theses.setPartialDocumentUrl(dto.getPartialDocumentUrl());
            theses.setFullDocumentUrl(dto.getFullDocumentUrl());
            theses.setStudents(studentsService.findById(dto.getStudents().getStudentId()));
        } else{
            theses = modelMapper.map(dto, Theses.class);
            repository.setSequence();
        }
        return repository.save(theses);
    }

    @Transactional
    public void delete(Long id) {
        Theses theses = repository.findById(id).get();
        repository.delete(theses);
    }

    public List<Theses> findByStudents(Students students) {
        return repository.findByStudents(students);
    }

    public String getUrlDocument(Theses theses){
        String url;
        if (UserSessionUtil.getUsername() != null){
            url = minioService.getLink(theses.getFullDocumentUrl(), Constants.DEFAULT_EXPIRY);
        } else{
            url = minioService.getLink(theses.getPartialDocumentUrl(), Constants.DEFAULT_EXPIRY);
        }
        return url;
    }
}
