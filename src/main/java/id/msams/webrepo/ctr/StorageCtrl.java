package id.msams.webrepo.ctr;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import javax.servlet.http.HttpServletResponse;

import com.toedter.spring.hateoas.jsonapi.JsonApiId;
import com.toedter.spring.hateoas.jsonapi.JsonApiTypeForClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import id.msams.webrepo.ctr.abs.JsonApiRequestMapping;
import id.msams.webrepo.ext.minio.MinioSrvc;
import id.msams.webrepo.ext.minio.MinioSrvc.UploadOption;
import io.minio.ObjectWriteResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@JsonApiRequestMapping("/storage")
public class StorageCtrl {

  private final MinioSrvc minio;

  @GetMapping("/{bucket}")
  public ResponseEntity<?> download(@PathVariable("bucket") String bucket, @RequestParam("file") String filename,
      HttpServletResponse res) {
    minio.view(res, bucket, filename);
    return ResponseEntity.status(HttpStatus.FOUND).build();
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonApiTypeForClass("file-upload-response")
  private static class ParsableObjectWriteResponse {

    @JsonApiId
    private String id;
    private String etag;
    private String versionId;
    private String bucket;
    private String region;
    private String object;

  }

  @PostMapping("/{bucket}")
  public ResponseEntity<?> upload(@PathVariable("bucket") String bucket, @Nullable @RequestParam("path") String path,
      @RequestPart("file") MultipartFile file) {
    ObjectWriteResponse res = minio.upload(file, bucket, o -> UploadOption.builder()
        .filename((path != null ? (path.endsWith("/") ? path : (path + "/")) : "") + System.currentTimeMillis() + "_-_"
            + o.getOriginalFilename().replace(" ", "_"))
        .build());
    return ResponseEntity.status(HttpStatus.CREATED).body(
        EntityModel.of(
            ParsableObjectWriteResponse.builder()
                .id(res.etag())
                .etag(res.etag())
                .versionId(res.versionId())
                .bucket(res.bucket())
                .region(res.region())
                .object(res.object())
                .build(),
            linkTo(methodOn(StorageCtrl.class).download(res.bucket(), res.object(), null)).withSelfRel()));
  }

}
