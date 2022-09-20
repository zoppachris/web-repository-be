package id.wg.webrepo.services;

import id.wg.webrepo.dtos.PagingDto;
import id.wg.webrepo.dtos.StudentsDto;
import id.wg.webrepo.dtos.ThesesDto;
import id.wg.webrepo.models.Students;
import id.wg.webrepo.models.Theses;
import id.wg.webrepo.repositories.StudentsRepository;
import id.wg.webrepo.repositories.ThesesRepository;
import id.wg.webrepo.security.UserSessionUtil;
import io.minio.errors.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    private StudentsRepository studentsRepository;

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
                        .partialDocumentUrl(t.getPartialDocumentUrl())
                        .fullDocumentUrl(getUrlDocument(t.getFullDocumentUrl()))
                        .students(modelMapper.map(studentsService.findByTheses(t), StudentsDto.class))
                        .fullTotalDownload(t.getFullTotalDownload())
                        .partialTotalDownload(t.getPartialTotalDownload())
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

        StudentsDto studentsDto = modelMapper.map(studentsService.findByTheses(theses), StudentsDto.class);
        ThesesDto dto = new ThesesDto();
        dto.setThesesId(theses.getThesesId());
        dto.setThesesTitle(theses.getThesesTitle());
        dto.setAbstracts(theses.getAbstracts());
        dto.setKeywords(theses.getKeywords());
        dto.setYear(theses.getYear());
        dto.setStudents(studentsDto);
        dto.setFullDocumentUrl(getUrlDocument(theses.getFullDocumentUrl()));
        dto.setPartialDocumentUrl(theses.getPartialDocumentUrl());
        dto.setFullTotalDownload(theses.getFullTotalDownload());
        dto.setPartialTotalDownload(theses.getPartialTotalDownload());
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
            repository.save(theses);
        } else{
            theses = modelMapper.map(dto, Theses.class);
            theses.setFullTotalDownload(0);
            theses.setPartialTotalDownload(0);
            repository.setSequence();
            theses = repository.save(theses);

            Students students = studentsService.findById(dto.getStudents().getStudentId());
            students.setTheses(theses);
            studentsRepository.save(students);
        }
        return theses;
    }

    @Transactional
    public void delete(Long id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Theses theses = repository.findById(id).get();

        Students students = studentsService.findByTheses(theses);
        students.setTheses(null);
        studentsRepository.save(students);

        minioService.delete(theses.getPartialDocumentUrl());
        minioService.delete(theses.getFullDocumentUrl());
        repository.delete(theses);
    }

    public String getUrlDocument(String path){
        if (UserSessionUtil.getUsername() != null){
            return path;
        } else {
            return "";
        }
    }

    @Transactional
    public void countDownloadTheses(Long id, boolean isPartial) {
        Optional<Theses> optional = repository.findById(id);
        if (optional.isPresent()){
            Theses theses = optional.get();
            if (isPartial) {
                int totalDownload = theses.getPartialTotalDownload();
                theses.setPartialTotalDownload(totalDownload+1);
            } else{
                int totalDownload = theses.getFullTotalDownload();
                theses.setFullTotalDownload(totalDownload+1);
            }
            repository.save(theses);
        }
    }
}
