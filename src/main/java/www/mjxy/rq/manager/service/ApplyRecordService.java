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
            dataJson.put("applyState", applyRecord.getApplyState());
            dataJson.put("trueName", applyRecord.getAppUser().getTrueName());
            dataJson.put("department", applyRecord.getAppUser().getDepartment());
            dataJson.put("schoolCode", applyRecord.getAppUser().getSchoolCode());
            data.add(dataJson);

        }
        return data;

    }

    /**
     * 处理请求
     * 通过
     * 拒绝
     */
    public JSONObject processApply(Long applyRecordId, String processSign) {

        JSONObject dataObject = new JSONObject();
        ApplyRecord applyRecord = applyRecordRepository.findOne(applyRecordId);
        if (applyRecord != null) {

            switch (processSign) {
                case "ACCESS":
                    applyRecord.setState(2);//通过
                    applyRecordRepository.save(applyRecord);
                    dataObject.put("state", 1);
                    dataObject.put("message", "已通过该申请!");
                    break;
                case "DENY":
                    applyRecord.setState(3);//拒绝
                    applyRecordRepository.save(applyRecord);
                    dataObject.put("state", 0);
                    dataObject.put("message", "已拒绝申请!");

                    break;
                default:
                    dataObject.put("state", 0);
                    dataObject.put("message", "操作标记只有ACCESS和DENY两种!");
                    break;
            }
        } else {
            dataObject.put("state", 0);
            dataObject.put("message", "该申请不存在!!");
        }

        return dataObject;
    }

    public JSONArray getAllApplyRecord() {
        JSONArray dataArray = new JSONArray();
        List<ApplyRecord> applyRecordList = applyRecordRepository.findAll();
        for (ApplyRecord applyRecord : applyRecordList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("applyState", applyRecord.getApplyState());
            jsonObject.put("date", applyRecord.getApplyDate());
            jsonObject.put("reason", applyRecord.getReason());
            jsonObject.put("state", applyRecord.getState());
            AppUser appUser = applyRecord.getAppUser();
            JSONObject userJson = new JSONObject();
            userJson.put("schoolCode", appUser.getSchoolCode());
            userJson.put("username", appUser.getUsername());
            userJson.put("department", appUser.getDepartment());
            userJson.put("phone", appUser.getPhone());
            jsonObject.put("userInfo", userJson);
            dataArray.add(jsonObject);
        }

        return dataArray;
    }
}
