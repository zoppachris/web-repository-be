package id.wg.webrepo.controllers;

import id.wg.webrepo.dtos.UsersDto;
import id.wg.webrepo.payload.Response;
import id.wg.webrepo.services.UsersService;
import id.wg.webrepo.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService service;

    @GetMapping()
    public Response<Object> read(@RequestParam(required = false) Long id,
                                 @RequestParam String search,
                                 @RequestParam String roles,
                                 Pageable pageable) {
        Response<Object> response = new Response<>();
        try {
            if (id != null){
                response.setSuccess(service.getById(id));
            }else{
                response.setSuccess(service.findAll(pageable, search, roles));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to get users");
        }
        return response;
    }

    @PostMapping()
    public Response<Object> create(@RequestBody UsersDto dto) {
        Response<Object> response = new Response<>();
        try {
            service.save(dto, null, Constants.ADMIN);
            response.setSuccess("User successfully created");
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to add user");
        }
        return response;
    }

    @PutMapping("/{id}")
    public Response<Object> update(@PathVariable Long id, @RequestBody UsersDto dto) {
        Response<Object> response = new Response<>();
        try {
            service.save(dto, id, null);
            response.setSuccess("User successfully updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to update user");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Response<Object> delete(@PathVariable Long id) {
        Response<Object> response = new Response<>();
        try {
            service.delete(id);
            response.setSuccess("User successfully deleted");
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to delete user");
        }
        return response;
    }
}
