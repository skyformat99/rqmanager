package www.mjxy.rq.manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.UserRole;

import java.util.List;

/**
 * Created by wwhai on 2018/2/23.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findAllByAppUser(AppUser appUser);

}
