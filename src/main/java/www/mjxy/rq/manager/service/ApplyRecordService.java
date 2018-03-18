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

    public JSONObject getRecordByRoom(Room room) {
        JSONObject returnJson = new JSONObject();
        ApplyRecord record = applyRecordRepository.findTopByRoom(room);
        JSONArray data = new JSONArray();
        List<ApplyRecord> applyRecordList = applyRecordRepository.findAllByRoom(room);
        for (ApplyRecord applyRecord : applyRecordList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("applyState", applyRecord.getApplyState());
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
        returnJson.put("roomState", record.getApply().getStateArray());
        returnJson.put("data", data);
        return returnJson;
    }

    public JSONArray getRecordByAppUser(AppUser appUser) {
        JSONArray data = new JSONArray();
        List<ApplyRecord> applyRecordList = applyRecordRepository.findAllByAppUser(appUser);
        for (ApplyRecord applyRecord : applyRecordList) {
            JSONObject dataJson = new JSONObject();
            dataJson.put("date", applyRecord.getApplyDate());
            dataJson.put("reason", applyRecord.getReason());
            dataJson.put("state", applyRecord.getState());
            dataJson.put("trueName", applyRecord.getAppUser().getTrueName());
            dataJson.put("department", applyRecord.getAppUser().getDepartment());
            dataJson.put("schoolCode", applyRecord.getAppUser().getSchoolCode());
            data.add(dataJson);

        }
        return data;

    }
}
