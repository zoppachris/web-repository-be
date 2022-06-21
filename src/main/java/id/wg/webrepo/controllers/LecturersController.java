package id.wg.webrepo.controllers;

import id.wg.webrepo.dtos.LecturersDto;
import id.wg.webrepo.payload.Response;
import id.wg.webrepo.services.LecturersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lecturers")
public class LecturersController {
    @Autowired
    private LecturersService service;

    @GetMapping()
    public Response<Object> read(@RequestParam(required = false) Long id, @RequestParam String search, Pageable pageable) {
        Response<Object> response = new Response<>();
        try {
            if (id != null){
                response.setSuccess(service.findById(id));
            }else{
                response.setSuccess(service.findAll(pageable, search));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to get lecturers");
        }
        return response;
    }

    @PostMapping()
    public Response<Object> create(@RequestBody LecturersDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.save(dto, null));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to add lecturer");
        }
        return response;
    }

    @PutMapping("/{id}")
    public Response<Object> update(@PathVariable Long id, @RequestBody LecturersDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.save(dto, id));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to update lecturer");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Response<Object> delete(@PathVariable Long id) {
        Response<Object> response = new Response<>();
        try {
            service.delete(id);
            response.setSuccess("Lecturer successfully deleted");
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to delete lecturer");
        }
        return response;
    }
}
