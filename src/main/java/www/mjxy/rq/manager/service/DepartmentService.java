package www.mjxy.rq.manager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.DepartmentRepository;
import www.mjxy.rq.manager.model.SchoolDepartment;

/**
 * Created by wwhai on 2018/3/10.
 */
@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public JSONArray findAllDepartments() {
        JSONArray schoolDepartments = new JSONArray();
        for (SchoolDepartment schoolDepartment : departmentRepository.findAll()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", schoolDepartment.getId());
            jsonObject.put("name", schoolDepartment.getName());
            schoolDepartments.add(jsonObject);
        }
        return schoolDepartments;

    }

}
