package www.mjxy.rq.manager.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by wwhai on 2018/2/23.
 */
@Entity
public class UserRole extends BaseEntity implements Serializable {

    @ManyToOne(targetEntity = AppUser.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    AppUser appUser;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
