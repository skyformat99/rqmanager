package www.mjxy.rq.manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import www.mjxy.rq.manager.model.Apply;

import java.util.Date;

/**
 * Created by wwhai on 2018/2/19.
 */
public interface ApplyRepository extends JpaRepository<Apply, Long> {


    Apply findTopByApplyDate(Date date);


}
