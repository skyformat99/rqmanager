package www.mjxy.rq.manager.model;

import javax.persistence.Entity;

/**院系Model
 * Created by wwhai on 2018/3/10.
 * 100000  电子系
 * 100002  计算机系
 * ..............
 */
@Entity
public class SchoolDepartment extends BaseEntity {
    private Long departmentCode;//院系代码
    private String name;

    public Long getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(Long departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
