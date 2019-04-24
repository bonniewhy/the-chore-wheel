package com.bonniewhy.thechorewheel.models;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3)
    private String username;

    @NotNull
    @Size(min = 3)
    private String password;

    // [ ] TODO: Figure out how to not save this to the database.
    @NotNull
    private String verifyPassword;

    @NotNull
    private String email;

    @NotNull
    @Size(min = 3)
    private String favoriteActivity;

    @ManyToMany
    private List<Room> rooms = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Task> tasks = new ArrayList<>();

    // Spring Security Methods
    // [ ] TODO: Figure out why using the encoder makes the password too long and how to fix it.
//    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

    // [ ] TODO: Figure out if this is the code I need to access to automatically log in new users.
    public static User getCurrentUser() {
        Object loggedInUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = (User) loggedInUser;
        return currentUser;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Constructors
    public User() { }

    public User(String username, String password, String email, String favoriteActivity) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.favoriteActivity = favoriteActivity;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getFavoriteActivity() {
        return favoriteActivity;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
        checkPassword();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFavoriteActivity(String favoriteActivity) {
        this.favoriteActivity = favoriteActivity;
    }

    // Other Methods
    private void checkPassword() {

        if (getPassword() != null && getVerifyPassword() != null) {
            if (!getPassword().equals(getVerifyPassword())) {
                setVerifyPassword(null);
            }
        }

    }

    // [ ] TODO: Write your own .equals() method.

    // [ ] TODO: Write your own .toString() method.

}
