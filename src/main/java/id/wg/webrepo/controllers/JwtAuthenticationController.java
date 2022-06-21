package id.wg.webrepo.controllers;

import id.wg.webrepo.dtos.ChangePasswordDto;
import id.wg.webrepo.dtos.LoginDto;
import id.wg.webrepo.payload.Response;
import id.wg.webrepo.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {
    @Autowired
    private LoginService service;

    @PostMapping("/login")
    public Response<Object> login(@RequestBody LoginDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.login(dto));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Login failed");
        }
        return response;
    }

    @PutMapping("/change-password")
    public Response<Object> changePassword(@RequestParam(required = false) boolean isSuperadmin,
                                           @RequestBody ChangePasswordDto dto) {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.changePassword(dto, isSuperadmin));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Change password failed");
        }
        return response;
    }
}
