package id.wg.webrepo.controllers;

import id.wg.webrepo.dtos.FacultiesDto;
import id.wg.webrepo.payload.Response;
import id.wg.webrepo.services.FacultiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculties")
public class FacultiesController {
    @Autowired
    private FacultiesService service;

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
            response.setError("Failed to get faculties");
        }
        return response;
    }

    @PostMapping()
    public Response<Object> create(@RequestBody FacultiesDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.save(dto, null));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to add faculty");
        }
        return response;
    }

    @PutMapping("/{id}")
    public Response<Object> update(@PathVariable Long id, @RequestBody FacultiesDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.save(dto, id));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to update faculty");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Response<Object> delete(@PathVariable Long id) {
        Response<Object> response = new Response<>();
        try {
            if (service.delete(id)){
                response.setSuccess("Faculty successfully deleted");
            }else{
                response.setError("Failed to delete faculty");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to delete faculty");
        }
        return response;
    }
}
