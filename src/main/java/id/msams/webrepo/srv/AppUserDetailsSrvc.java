package id.msams.webrepo.srv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import id.msams.webrepo.dao.sec.UserRepo;
import id.msams.webrepo.ext.i18n.utility.MessageUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppUserDetailsSrvc implements UserDetailsService {

  private final UserRepo userRepo;

  private final MessageUtil msg;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepo.findOneByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(msg.get("SYSTEM.ERROR.AUTH.INVALID-CREDENTIALS.DETAIL")));
  }

}
