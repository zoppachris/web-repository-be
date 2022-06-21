package id.wg.webrepo.services;

import id.wg.webrepo.dtos.HomepageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomepageService {
    @Autowired
    private ThesesService thesesService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private LecturersService lecturersService;

    public HomepageDto getHomepage(){
        HomepageDto dto = new HomepageDto();
        dto.setTotalRepository(thesesService.getTotalActive());
        dto.setTotalDosen(lecturersService.getTotalActive());
        dto.setTotalMahasiswa(studentsService.getTotalActive());
        return dto;
    }
}
