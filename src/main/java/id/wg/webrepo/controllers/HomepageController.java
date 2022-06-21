package id.wg.webrepo.controllers;

import id.wg.webrepo.payload.Response;
import id.wg.webrepo.services.HomepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/homepage")
public class HomepageController {
    @Autowired
    private HomepageService service;

    @GetMapping()
    public Response<Object> read() {
        Response<Object> response = new Response<>();
        try {
            response.setSuccess(service.getHomepage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to get homepage");
        }
        return response;
    }
}
