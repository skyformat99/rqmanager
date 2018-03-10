package www.mjxy.rq.manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import www.mjxy.rq.manager.model.SchoolDepartment;

/**
 * Created by wwhai on 2018/3/10.
 */
public interface DepartmentRepository extends JpaRepository<SchoolDepartment, Long> {
}
