package www.mjxy.rq.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.UserRole;

import java.util.List;

/**
 * Created by wwhai on 2018/2/19.
 */
public class AppUserDetailService implements UserDetailsService {
    @Autowired
    AppUserService appUserService;
    @Autowired
    UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String parameter) throws UsernameNotFoundException {

        /**
         * 可以用Username登录
         */

        AppUser appUser;
        try {
            appUser = appUserService.getUserByParameter( parameter);
            List<UserRole> roleList = userRoleService.getByAppUser(appUser);
            appUser.setRoleList(roleList);
            return appUser;
        } catch (Exception e) {
            //e.printStackTrace();
            throw e;
        }

    }
}
