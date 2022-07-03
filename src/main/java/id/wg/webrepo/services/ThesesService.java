package id.wg.webrepo.services;

import id.wg.webrepo.dtos.PagingDto;
import id.wg.webrepo.dtos.StudentsDto;
import id.wg.webrepo.dtos.ThesesDto;
import id.wg.webrepo.models.Students;
import id.wg.webrepo.models.Theses;
import id.wg.webrepo.repositories.ThesesRepository;
import id.wg.webrepo.security.UserSessionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
                        .partialDocumentPath(minioService.getLink(t.getPartialDocumentUrl()))
                        .fullDocumentPath(getUrlFullDocument(t.getFullDocumentUrl()))
                        .partialDocumentUrl(t.getPartialDocumentUrl())
                        .fullDocumentUrl(t.getFullDocumentUrl())
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

        StudentsDto studentsDto = modelMapper.map(theses.getStudents(), StudentsDto.class);
        ThesesDto dto = new ThesesDto();
        dto.setThesesId(theses.getThesesId());
        dto.setThesesTitle(theses.getThesesTitle());
        dto.setAbstracts(theses.getAbstracts());
        dto.setKeywords(theses.getKeywords());
        dto.setYear(theses.getYear());
        dto.setStudents(studentsDto);
        dto.setFullDocumentUrl(theses.getFullDocumentUrl());
        dto.setPartialDocumentUrl(theses.partialDocumentUrl);
        dto.setPartialDocumentPath(minioService.getLink(theses.getPartialDocumentUrl()));
        dto.setFullDocumentPath(getUrlFullDocument(theses.getFullDocumentUrl()));
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
            if (!StringUtils.isEmpty(dto.getPartialDocumentUrl())){
                theses.setPartialDocumentUrl(dto.getPartialDocumentUrl());
            }
            if (!StringUtils.isEmpty(dto.getFullDocumentUrl())){
                theses.setFullDocumentUrl(dto.getFullDocumentUrl());
            }
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

    public String getUrlFullDocument(String document){
        String url = "";
        if (UserSessionUtil.getUsername() != null){
            url = minioService.getLink(document);
        }
        return url;
    }
}
