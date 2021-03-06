package www.mjxy.rq.manager.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by wwhai on 2018/2/19.
 */
@Entity
public class AppUser extends BaseEntity implements UserDetails, Serializable {

    private String username;
    @JsonIgnore
    private String password;
    //private String schoolCode;
    private String email;
    private String phone;
    private String trueName;//真实姓名

    private String department;//院系
    private String avatar = "/avatar/avatar" + new Random().nextInt(51) + ".png";
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @OneToMany(targetEntity = UserRole.class, fetch = FetchType.LAZY)
    private List<ApplyRecord>applyRecords;

    @JSONField(serialize = false)
    @OneToMany(targetEntity = UserRole.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserRole> roleList;

    public List<UserRole> getRoleList() {
        return roleList;
    }

    public String getDepartment() {
        return department;
    }

    public String getTrueName() {
        return trueName;
    }

    public List<ApplyRecord> getApplyRecords() {
        return applyRecords;
    }

    public void setApplyRecords(List<ApplyRecord> applyRecords) {
        this.applyRecords = applyRecords;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /**
         * 默认给了一个普通用户
         */
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        for (UserRole role : roleList) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return simpleGrantedAuthorities;
    }


    public void setRoleList(List<UserRole> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }


    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
