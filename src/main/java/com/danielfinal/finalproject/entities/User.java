package com.danielfinal.finalproject.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity (name = "User")
@Table (
        name = "tbl_user",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "unique_username",columnNames = "username"
            ),
            @UniqueConstraint(
                    name = "unique_dni", columnNames = "dni"
            )
        }
)
public class User implements UserDetails{

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "user_id",
            updatable = false
    )
    private long id;

    @Column (
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @Column (
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @Column (
            name = "username",
            nullable = false
    )
    private String username;

    @Column (
            name = "password",
            nullable = false
    )
    private String password;

    @Column (
            name = "dni",
            nullable = false
    )
    private String dni;

    @Column (
            name = "address",
            nullable = false
    )
    private String address;

    @Column (
            name = "zip_code",
            nullable = false
    )
    private String zipCode;

    @Column (
            name = "city",
            nullable = false
    )
    private String city;

    @Column (
            name = "state",
            nullable = false
    )
    private String state;

    @Column (
            name = "country",
            nullable = false
    )
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "role",
            nullable = false
    )
    private UserRole userRole;

    private Boolean locked = false;
    private Boolean enabled =false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());

        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
