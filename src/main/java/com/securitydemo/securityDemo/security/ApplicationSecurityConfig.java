package com.securitydemo.securityDemo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
           //     .csrf().disable().
                authorizeRequests().
                antMatchers("/", "index").permitAll().
                antMatchers("/api/**").hasRole(ApplicationUserRole.USER.name()).
               /* antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.USER_WRITE.getPermission()).
                antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.USER_WRITE.getPermission()).
                antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.USER_WRITE.getPermission()).
                antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRINEE.name()).*/
                anyRequest().
                authenticated().
                and().
                httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails chaitanya = User.builder().
                username("chaitanya").
                password(passwordEncoder.encode("password")).
           //     roles(ApplicationUserRole.USER.name()).
                   authorities(ApplicationUserRole.USER.getGrantedAuthorities()).
                build();
        UserDetails nagesh = User.builder().
                username("nagesh").
                password(passwordEncoder.encode("password123")).
         //       roles(ApplicationUserRole.ADMIN.name()).
                 authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities()).
                build();
        UserDetails tom = User.builder().
                username("tom").
                password(passwordEncoder.encode("password123")).
        //        roles(ApplicationUserRole.ADMINTRINEE.name()).
                authorities(ApplicationUserRole.ADMINTRINEE.getGrantedAuthorities()).
                build();


        return new InMemoryUserDetailsManager(
                chaitanya,
                nagesh,
                tom
        );

    }
}
