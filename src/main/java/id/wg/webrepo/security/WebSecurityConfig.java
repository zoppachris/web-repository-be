package id.wg.webrepo.security;

import id.wg.webrepo.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/api/oas/**", "/api/swagger/**", "/api/swagger-ui/**", "/api/explorer/**").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/change-password").permitAll()
                .antMatchers("/api/homepage").permitAll()
                .antMatchers("/api/majors/lov").permitAll()
                .antMatchers("/api/download").permitAll()
                .antMatchers(HttpMethod.GET,"/api/theses").permitAll()
                .antMatchers(HttpMethod.GET,"/api/majors").permitAll()
                .antMatchers(HttpMethod.GET,"/api/students").permitAll()
                .antMatchers(HttpMethod.POST, "/api/theses/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/theses/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/theses/**").hasAnyRole(Constants.SUPERADMIN, Constants.ADMIN)
                .antMatchers("/api/faculties/**").hasAnyRole(Constants.SUPERADMIN, Constants.ADMIN)
                .antMatchers("/api/majors/**").hasAnyRole(Constants.SUPERADMIN, Constants.ADMIN)
                .antMatchers("/api/lecturers/**").hasAnyRole(Constants.SUPERADMIN, Constants.ADMIN)
                .antMatchers("/api/students/**").hasAnyRole(Constants.SUPERADMIN, Constants.ADMIN)
                .antMatchers("/api/upload/**").permitAll()
                .antMatchers("/api/users/**").hasRole(Constants.SUPERADMIN)
                .anyRequest().authenticated()
                .and()
                .cors().and()
                .csrf().disable();

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
