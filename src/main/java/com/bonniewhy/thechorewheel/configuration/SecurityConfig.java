package com.bonniewhy.thechorewheel.configuration;

import com.bonniewhy.thechorewheel.models.User;
import com.bonniewhy.thechorewheel.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDao userDao;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/signup", "/signin", "/css/*", "/js/*", "/images/*").permitAll()
                .antMatchers("/room/add").hasAuthority("USER")
                .antMatchers("/**").hasAuthority("USER")

                .and().formLogin()
                .loginPage("/signin")
                .defaultSuccessUrl("/")

                .and().logout()
                .logoutUrl("/signout")
                .logoutSuccessUrl("/");

    }

    @Autowired
    DataSource dataSource;

    // [ ] TODO: Delve deeper into Spring Security and figure out how to set roles.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userDao.findByUsername(username);
            }
        });
//                .passwordEncoder(User.passwordEncoder);


    }
}
