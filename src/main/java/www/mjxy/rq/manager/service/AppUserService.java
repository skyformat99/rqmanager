package www.mjxy.rq.manager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.AppUserRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.UserRole;

import java.util.List;

/**
 * Created by wwhai on 2018/2/19.
 */
@Service
public class AppUserService {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    ApplyRecordService applyRecordService;


    public AppUser getUserByParameter(String username, String schoolCode) {
        AppUser appUser;
        try {
            appUser = appUserRepository.findTopByUsernameOrSchoolCode(username, schoolCode);
            List<UserRole> userRoleList = userRoleService.getByAppUser(appUser);
            if (userRoleList == null || userRoleList.size() < 1) {
                appUser.setRoleList(null);
            } else {
                appUser.setRoleList(userRoleList);
            }
        } catch (Exception e) {
            return null;

        }

        return appUser;
    }

    public boolean isUsernameExist(String username) {
        return appUserRepository.findTopByUsername(username) == null ? false : true;

    }


    public AppUser updateUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    public AppUser createUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    public JSONArray getAllUserApplyData() {
        JSONArray data = new JSONArray();
        for (AppUser appUser : appUserRepository.findAll()) {
            JSONObject userJson = new JSONObject();
            userJson.put("username", appUser.getUsername());
            userJson.put("trueName", appUser.getTrueName());
             userJson.put("department", appUser.getDepartment());
            userJson.put("phone", appUser.getPhone());
            userJson.put("email", appUser.getEmail());
            JSONArray applyRecordList = applyRecordService.getRecordByAppUser(appUser);
            int successCount = 0;
            int failureCount = 0;
            for (Object o : applyRecordList) {
                System.out.printf("State:" + ((JSONObject) o).getIntValue("state"));
                if (((JSONObject) o).getString("state") == "2") {
                    successCount += 1;
                } else if (((JSONObject) o).getString("state") .equals("0")  || ((JSONObject) o).getString("state") .equals("1")) {
                    failureCount += 1;
                }

            }
            userJson.put("totalCount", applyRecordService.getRecordByAppUser(appUser).size());
            userJson.put("failureCount", failureCount);
            userJson.put("successCount", successCount);
            data.add(userJson);
        }

        return data;

    }

}
