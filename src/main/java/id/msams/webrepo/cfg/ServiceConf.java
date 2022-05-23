package id.msams.webrepo.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import id.msams.webrepo.dao.dat.Thesis;
import id.msams.webrepo.dao.dat.ThesisRepo;
import id.msams.webrepo.srv.abs.CrudService;
import id.msams.webrepo.srv.abs.CrudServiceImpl;

@Configuration
public class ServiceConf {

  @Bean
  public CrudService<Thesis, Long> thesisCrudService(ThesisRepo repo) {
    return new CrudServiceImpl<>(repo);
  }

}
