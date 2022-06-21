package id.wg.webrepo.controllers;

import id.wg.webrepo.payload.Response;
import id.wg.webrepo.services.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class MinioController {
    @Autowired
    private MinioService service;

    @PutMapping("/upload")
    public Response<Object> upload(@RequestParam MultipartFile file,
                                   @RequestParam String username,
                                   @RequestParam String oldpath) {
        Response<Object> response = new Response<>();
        try {
            service.delete(oldpath);
            response.setSuccess(service.upload(file,username).object());
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("Failed to upload file");
        }
        return response;
    }
}
