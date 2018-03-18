package www.mjxy.rq.manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import www.mjxy.rq.manager.model.DailyLog;

/**
 * Created by wwhai on 2018/3/18.
 */
public interface DailyLogRepository extends JpaRepository<DailyLog,Long> {
}
