package www.mjxy.rq.manager.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.Apply;
import www.mjxy.rq.manager.model.Room;

import java.util.Date;
import java.util.List;

/**
 * Created by wwhai on 2018/2/19.
 */
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    Page<Apply> findAllByAppUser(AppUser appUser, Pageable pageable);

    List<Apply> findAllByRoomAndAppUser(Room room, AppUser appUser);

    Apply findByIdAndAppUser(Long applyId, AppUser appUser);

    List<Apply> findAllByRoom(Room room);

    List<AppUser> findAllByAppUser(AppUser appUser);


    Apply findTopByApplyDateBetween( Date start, Date end);
}
