package id.wg.webrepo.controllers;

import id.wg.webrepo.dtos.StudentsDto;
import id.wg.webrepo.payload.Response;
import id.wg.webrepo.services.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentsController {
    @Autowired
    private StudentsService service;

    @GetMapping()
    public Response<Object> read(@RequestParam(required = false) Long id,
                                 @RequestParam(required = false) Boolean hasTheses,
                                 @RequestParam String search,
                                 @RequestParam String jurusan, Pageable pageable) {
        Response<Object> response = new Response<>();
        try {
            if (id != null){
                response.setSuccess(service.findById(id));
            }else{
                response.setSuccess(service.findAll(pageable, search, jurusan, hasTheses));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to get students");
        }
        return response;
    }

    @PostMapping()
    public Response<Object> create(@RequestBody StudentsDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.save(dto, null));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to add student");
        }
        return response;
    }

    @PutMapping("/{id}")
    public Response<Object> update(@PathVariable Long id, @RequestBody StudentsDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.save(dto, id));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to update student");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Response<Object> delete(@PathVariable Long id) {
        Response<Object> response = new Response<>();
        try {
            if (service.delete(id)){
                response.setSuccess("Student successfully deleted");
            }else{
                response.setError("Failed to delete student");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to delete student");
        }
        return response;
    }
}
