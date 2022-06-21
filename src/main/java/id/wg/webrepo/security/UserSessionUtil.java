package id.wg.webrepo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserSessionUtil {
	public static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getName().equals("anonymousUser")) {
			return null;
		} else {
			return authentication.getName();
		}
	}

	public static boolean isAuthenticated() {
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}
}
