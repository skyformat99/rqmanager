package www.mjxy.rq.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.UserRoleRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.UserRole;

import java.util.List;

/**
 * Created by wwhai on 2018/2/23.
 */
@Service
public class UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;


    public List<UserRole> getByAppUser(AppUser appUser) {
        return userRoleRepository.findAllByAppUser(appUser);
    }

    public UserRole createUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }


}
