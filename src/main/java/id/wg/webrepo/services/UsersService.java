package id.wg.webrepo.services;

import id.wg.webrepo.dtos.PagingDto;
import id.wg.webrepo.dtos.UsersDto;
import id.wg.webrepo.models.RoleUser;
import id.wg.webrepo.models.Roles;
import id.wg.webrepo.models.Users;
import id.wg.webrepo.repositories.RoleUserRepository;
import id.wg.webrepo.repositories.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsersService {
    @Autowired
    private UsersRepository repository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<PagingDto> findAll(Pageable pageable, String search) {
        return Optional.of(repository.findAll(pageable, search))
                .map(pages -> PagingDto.builder()
                        .maxPage(pages.getTotalPages() == 0 ? 0 : pages.getTotalPages() - 1)
                        .page(pageable.getPageNumber())
                        .size(pageable.getPageSize())
                        .totalElements(pages.getTotalElements())
                        .content(toResponseDto(pages.getContent()))
                        .build());
    }

    public List<UsersDto> toResponseDto(List<Users> users) {
        return users.stream()
                .map(u -> UsersDto.builder()
                        .userId(u.getUserId())
                        .userName(u.getUserName())
                        .name(u.getName())
                        .password(null)
                        .status(u.status)
                        .build())
                .collect(Collectors.toList());
    }

    public Users findById(Long id) {
        Optional<Users> optional = repository.findById(id);
        Users user = new Users();
        if (optional.isPresent()){
            user = optional.get();
            user.setPassword(null);
        }
        return user;
    }

    @Transactional
    public Users save(UsersDto dto, Long id, String name) {
        Users user;
        if (id != null){
            user = findById(id);
            user.setName(dto.getName());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setStatus(dto.isStatus());
            repository.save(user);
        } else{
            user = modelMapper.map(dto, Users.class);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            repository.setSequence();
            repository.save(user);

            saveRoleUser(user, name);
        }
        return user;
    }

    @Transactional
    public void saveRoleUser(Users user, String name) {
        Roles role = rolesService.findByName(name);

        RoleUser roleUser = new RoleUser();
        roleUser.setUsers(user);
        roleUser.setRole(role);

        roleUserRepository.setSequence();
        roleUserRepository.save(roleUser);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
