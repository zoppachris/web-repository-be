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
import org.springframework.util.StringUtils;

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

    public Optional<PagingDto> findAll(Pageable pageable, String search, String roles) {
        return Optional.of(repository.findAll(pageable, search, roles))
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
                        .roleName(findRoleName(u))
                        .build())
                .collect(Collectors.toList());
    }

    public UsersDto findById(Long id) {
        Users users = repository.findById(id).get();
        UsersDto dto = modelMapper.map(users, UsersDto.class);
        dto.setRoleName(findRoleName(users));
        return dto;
    }

    @Transactional
    public Users save(UsersDto dto, Long id, String name) {
        Users user;
        if (id != null){
            user = repository.findById(id).get();
            user.setName(dto.getName());
            if (!StringUtils.isEmpty(dto.getPassword())){
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            user.setUserName(dto.getUserName());
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
        roleUser.setRoles(role);

        roleUserRepository.setSequence();
        roleUserRepository.save(roleUser);
    }

    @Transactional
    public void delete(Long id) {
        Users user = repository.findById(id).get();
        repository.delete(user);
    }

    public String findRoleName(Users users) {
        RoleUser roleUser = roleUserRepository.findByUsers(users);
        return roleUser.getRoles().getRoleName();
    }
}
