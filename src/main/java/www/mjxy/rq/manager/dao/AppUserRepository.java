package www.mjxy.rq.manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.Apply;

import java.util.List;

/**
 * Created by wwhai on 2018/2/19.
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findTopByUsername(String username);

}
