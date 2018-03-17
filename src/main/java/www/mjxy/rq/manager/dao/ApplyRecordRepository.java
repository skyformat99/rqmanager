package www.mjxy.rq.manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.ApplyRecord;
import www.mjxy.rq.manager.model.Room;

import java.util.List;

/**
 * Created by wwhai on 2018/3/17.
 */
public interface ApplyRecordRepository extends JpaRepository<ApplyRecord, Long> {

    List<ApplyRecord> findAllByRoom(Room room);

    List<ApplyRecord> findAllByAppUser(AppUser appUser);
}
