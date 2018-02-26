package www.mjxy.rq.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.AppUserRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.UserRole;

import java.util.List;

/**
 * Created by wwhai on 2018/2/19.
 */
@Service
public class AppUserService {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    UserRoleService userRoleService;

    public boolean isUserExistsByParameter(String username, String schoolCode) {

        return (appUserRepository.findTopByUsernameOrSchoolCode(username, schoolCode) == null) ? false : true;
    }


    public AppUser getUserByParameter(String username, String schoolCode) {
        AppUser appUser;
        try {
            appUser = appUserRepository.findTopByUsernameOrSchoolCode(username, schoolCode);
            List<UserRole> userRoleList = userRoleService.getByAppUser(appUser);
            if (userRoleList == null || userRoleList.size() < 1) {
                appUser.setRoleList(null);
            } else {
                appUser.setRoleList(userRoleList);
            }
        } catch (Exception e) {
            return null;

        }

        return appUser;
    }




    public AppUser updateUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    public AppUser createUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }


}
