package www.mjxy.rq.manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.Room;

import java.util.List;

/**
 * Created by wwhai on 2018/2/19.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

}
