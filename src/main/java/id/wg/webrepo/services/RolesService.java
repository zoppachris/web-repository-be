package id.wg.webrepo.services;

import id.wg.webrepo.models.Roles;
import id.wg.webrepo.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RolesService {
    @Autowired
    private RolesRepository repository;

    public Roles findByName(String name) {
        Optional<Roles> optional = repository.findByName(name);
        Roles roles = new Roles();
        if (optional.isPresent()){
            roles = optional.get();
        }
        return roles;
    }
}
