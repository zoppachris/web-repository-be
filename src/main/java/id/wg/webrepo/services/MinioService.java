package id.wg.webrepo.services;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MinioService {
    @Value("${application.minio.username}")
    protected String username;

    @Value("${application.minio.password}")
    protected String password;

    @Value("${application.minio.url}")
    protected String url;

    @Value("${application.minio.bucket}")
    protected String bucket;

    public MinioClient minio() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(username, password)
                .build();
    }

    public String getLink(String filename) {
        String urlDownload = "https://api-minio.unnur-repository.com";
        String url = "";
        try {
            url = minio().getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(bucket)
                                    .object(filename)
                                    .expiry(2, TimeUnit.HOURS)
                                    .build());
            url = url.replace(this.url, urlDownload);
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                 | InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException
                 | IllegalArgumentException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return url;
        }
        return url;
    }

    public ObjectWriteResponse upload(MultipartFile file, String username) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UUID uuid = UUID.randomUUID();

        String filename = username + "/" + uuid + "-" + file.getOriginalFilename();
        return minio().putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(filename)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
    }

    public void delete(String filename) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<DeleteObject> oldObjects = new LinkedList<>();
        oldObjects.add(new DeleteObject(filename));

        Iterable<Result<DeleteError>> results = minio().removeObjects(RemoveObjectsArgs.builder()
                .bucket(bucket)
                .objects(oldObjects)
                .build());

        for (Result<DeleteError> result : results) {
            DeleteError error = result.get();
            System.out.println("Error in deleting object " + error.objectName() + "; " + error.message());
        }
    }
}
