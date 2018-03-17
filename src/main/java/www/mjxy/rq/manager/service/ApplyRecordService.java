package www.mjxy.rq.manager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.ApplyRecordRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.ApplyRecord;
import www.mjxy.rq.manager.model.Room;

import java.util.List;

/**
 * Created by wwhai on 2018/3/17.
 */
@Service("ApplyRecordService")
public class ApplyRecordService {
    @Autowired
    ApplyRecordRepository applyRecordRepository;

    public void save(ApplyRecord applyRecord) {
        applyRecordRepository.save(applyRecord);
    }

    public JSONArray getRecordByRoom(Room room) {
        JSONArray data = new JSONArray();
        List<ApplyRecord> applyRecordList = applyRecordRepository.findAllByRoom(room);
        for (ApplyRecord applyRecord : applyRecordList) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("reason", applyRecord.getReason());
            jsonObject.put("date", applyRecord.getApplyDate());
            jsonObject.put("state", applyRecord.getState());


            AppUser appUser = applyRecord.getAppUser();
            JSONObject userJson = new JSONObject();
            userJson.put("username", appUser.getUsername());
            userJson.put("schoolCode", appUser.getSchoolCode());
            userJson.put("department", appUser.getDepartment());
            userJson.put("phone", appUser.getPhone());

            jsonObject.put("userInfo", userJson);
            data.add(jsonObject);
        }
        return data;
    }

    public JSONArray getRecordByAppUser(AppUser appUser) {
        JSONArray data = new JSONArray();
        List<ApplyRecord> applyRecordList = applyRecordRepository.findAllByAppUser(appUser);
        for (ApplyRecord applyRecord : applyRecordList) {
            JSONObject dataJson = new JSONObject();
            dataJson.put("pa", applyRecord.getApplyDate());
            dataJson.put("aa", applyRecord.getReason());
            dataJson.put("acc", applyRecord.getAppUser().getTrueName());
            data.add(dataJson);

        }
        return data;

    }
}
