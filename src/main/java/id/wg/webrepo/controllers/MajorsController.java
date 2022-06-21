package id.wg.webrepo.controllers;

import id.wg.webrepo.dtos.MajorsDto;
import id.wg.webrepo.payload.Response;
import id.wg.webrepo.services.MajorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/majors")
public class MajorsController {
    @Autowired
    private MajorsService service;

    @GetMapping("/lov")
    public Response<Object> lov() {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.getLov());
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to get lov majors");
        }
        return response;
    }

    @GetMapping()
    public Response<Object> read(@RequestParam(required = false) Long id, @RequestParam String search,
                                 @RequestParam String fakultas, Pageable pageable) {
        Response<Object> response = new Response<>();
        try {
            if (id != null){
                response.setSuccess(service.findById(id));
            }else{
                response.setSuccess(service.findAll(pageable, search, fakultas));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to get majors");
        }
        return response;
    }

    @PostMapping()
    public Response<Object> create(@RequestBody MajorsDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.save(dto, null));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to add major");
        }
        return response;
    }

    @PutMapping("/{id}")
    public Response<Object> update(@PathVariable Long id, @RequestBody MajorsDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.save(dto, id));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to update major");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Response<Object> delete(@PathVariable Long id) {
        Response<Object> response = new Response<>();
        try {
            if (service.delete(id)){
                response.setSuccess("Major successfully deleted");
            }else{
                response.setError("Failed to delete major");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to delete major");
        }
        return response;
    }
}
