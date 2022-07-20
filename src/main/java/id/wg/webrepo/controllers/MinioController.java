package id.wg.webrepo.controllers;

import id.wg.webrepo.payload.Response;
import id.wg.webrepo.services.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @GetMapping(path = "/download")
    public ResponseEntity<ByteArrayResource> download(@RequestParam String filename) {
        byte[] data = service.getFile(filename);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + filename + "\"")
                .body(resource);

    }
}
