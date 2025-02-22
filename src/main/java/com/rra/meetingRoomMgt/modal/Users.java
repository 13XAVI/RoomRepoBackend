package com.rra.meetingRoomMgt.modal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StaffID")
    private Integer staffID;

    @Column(name = "userName", nullable = false)
    private String fullnames;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "emp_no", nullable = false, unique = true)
    private String empNo;

    @Column(name = "user_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_no_sequence")
    @SequenceGenerator(name = "user_no_sequence", sequenceName = "user_no_seq", allocationSize = 1)
    private Integer userNo;

    @Column(name = "mbl_no", nullable = false)
    private String mobileNo;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_status")
    private String userStatus;


    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Units units;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Departments departments;

    @Column(name = "lgn_fail_count")
    private Integer loginFailCount;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "users")
    @JsonIgnore
    private ForgotPassword forgotPassword;

    public Users(Integer userNo) {
        this.userNo = userNo;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_no"),
            inverseJoinColumns = @JoinColumn(name = "Authority_no")
    )
    private Set<Authority> authorities = new HashSet<>();


//    I added a Set<User_Authority> field named userAuthorities to represent the user's authorities.
//    In the getAuthorities method, I iterate over userAuthorities and create SimpleGrantedAuthority instances using the authorityName
//    from the associated Authority entity.
//    The GrantedAuthority instances are added to a Set to ensure uniqueness.
//    The Set is then returned as the result of getAuthorities.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (this.authorities != null) {
            for (Authority authority : this.authorities) {
                authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
            }
        }
        return authorities;
//        return null;
    }

    @Override
    public String getUsername() {
        // email in our case
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }


}
