package id.wg.webrepo.controllers;

import id.wg.webrepo.dtos.ThesesDto;
import id.wg.webrepo.payload.Response;
import id.wg.webrepo.services.ThesesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/theses")
public class ThesesController {
    @Autowired
    private ThesesService service;

    @GetMapping()
    public Response<Object> read(@RequestParam(required = false) Long id, @RequestParam String search,
                                 @RequestParam String jurusan,  @RequestParam String year, Pageable pageable) {
        Response<Object> response = new Response<>();
        try {
            if (id != null){
                response.setSuccess(service.findById(id));
            }else{
                response.setSuccess(service.findAll(pageable, search, year, jurusan));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to get theses");
        }
        return response;
    }

    @PostMapping()
    public Response<Object> create(@RequestBody ThesesDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.save(dto, null));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to add theses");
        }
        return response;
    }

    @PutMapping("/{id}")
    public Response<Object> update(@PathVariable Long id, @RequestBody ThesesDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.save(dto, id));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to update theses");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Response<Object> delete(@PathVariable Long id) {
        Response<Object> response = new Response<>();
        try {
            service.delete(id);
            response.setSuccess("Theses successfully deleted");
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to delete theses");
        }
        return response;
    }
}
