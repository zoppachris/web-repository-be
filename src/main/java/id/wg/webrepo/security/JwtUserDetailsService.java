package id.wg.webrepo.security;

import id.wg.webrepo.models.RoleUser;
import id.wg.webrepo.models.Users;
import id.wg.webrepo.repositories.RoleUserRepository;
import id.wg.webrepo.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        RoleUser roleUser = roleUserRepository.findByUsers(users);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.
                commaSeparatedStringToAuthorityList("ROLE_"+roleUser.getRole().getRoleName());

        return new org.springframework.security.core.userdetails.User(
                users.getUserName(),
                users.getPassword(),
                grantedAuthorities);
    }
}
