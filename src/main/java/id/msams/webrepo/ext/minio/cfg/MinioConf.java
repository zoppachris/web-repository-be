package id.msams.webrepo.ext.minio.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioConf {
  @Bean
  public MinioClient minioClient(MinioProp props) {
    return MinioClient.builder()
        .endpoint(props.getUrl())
        .credentials(props.getUsername(), props.getPassword())
        .build();
  }
}
