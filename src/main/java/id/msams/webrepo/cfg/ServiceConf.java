package id.msams.webrepo.cfg;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;

import id.msams.webrepo.dao.dat.Thesis;
import id.msams.webrepo.dao.dat.ThesisRepo;
import id.msams.webrepo.srv.abs.CrudService;
import id.msams.webrepo.srv.abs.CrudServiceImpl;

@Configuration
public class ServiceConf {

  @Bean
  public CrudService<Thesis, Long, ?> thesisCrudService(ModelMapper mdlMap, ThesisRepo repo,
      PagedResourcesAssembler<Thesis> resourceAssembler) {
    return new CrudServiceImpl<>(mdlMap, repo, resourceAssembler);
  }

}
