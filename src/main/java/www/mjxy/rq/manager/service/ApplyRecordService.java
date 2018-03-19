package www.mjxy.rq.manager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.AppUserRepository;
import www.mjxy.rq.manager.dao.ApplyRecordRepository;
import www.mjxy.rq.manager.dao.ApplyRepository;
import www.mjxy.rq.manager.model.*;

import java.util.*;

/**
 * Created by wwhai on 2018/3/17.
 */
@Service("ApplyRecordService")
public class ApplyRecordService {
    @Autowired
    DailyLogService dailyLogService;
    @Autowired
    ApplyRecordRepository applyRecordRepository;

    @Autowired
    ApplyRepository applyRepository;

    @Autowired
    AppUserRepository appUserRepository;

    public void save(ApplyRecord applyRecord) {
        applyRecordRepository.save(applyRecord);
    }

    public JSONObject getRecordByRoom(Room room) {
        JSONObject returnJson = new JSONObject();
        ApplyRecord record = applyRecordRepository.findTopByRoom(room);
        JSONArray data = new JSONArray();
        List<ApplyRecord> applyRecordList = applyRecordRepository.findAllByRoom(room);
        for (ApplyRecord applyRecord : applyRecordList) {
            if (applyRecord.getState() != 0) {//排除被拒绝的
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
     * 通过 请求 2 房间状态为2使用中
     * 拒绝 请求 0 房间状态复原
     */
    public JSONObject processApply(Long applyRecordId, String processSign) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        JSONObject dataObject = new JSONObject();
        ApplyRecord applyRecord = applyRecordRepository.findOne(applyRecordId);
        if (applyRecord != null) {
            Apply apply = applyRecord.getApply();
            switch (processSign) {
                case "ACCESS":
                    applyRecord.setState(2);//通过
                    applyRecordRepository.save(applyRecord);
                    for (int i = 0; i < apply.getStateArray().toCharArray().length; i++) {
                        if (applyRecord.getApplyState().toCharArray()[i] == '1') {
                            char stateChararray[] = apply.getStateArray().toCharArray();
                            stateChararray[i] = '2';
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int j = 0; j < stateChararray.length; j++) {
                                stringBuffer.append(stateChararray[j]);
                            }
                            apply.setStateArray(stringBuffer.toString());
                            applyRepository.save(apply);

                        }
                    }

                    dataObject.put("state", 1);
                    dataObject.put("message", "已通过该申请!");
                    DailyLog dailyLog = new DailyLog("管理员[" + appUser.getUsername(),
                            "[通过申请]", "[成功]");
                    dailyLogService.save(dailyLog);
                    break;
                case "DENY":
                    applyRecord.setState(0);//拒绝
                    applyRecordRepository.save(applyRecord);
                    /**
                     * i位 置0
                     */
                    for (int i = 0; i < apply.getStateArray().toCharArray().length; i++) {
                        if (applyRecord.getApplyState().toCharArray()[i] == '1') {
                            char stateChararray[] = apply.getStateArray().toCharArray();
                            stateChararray[i] = '0';
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int j = 0; j < stateChararray.length; j++) {
                                stringBuffer.append(stateChararray[j]);
                            }
                            apply.setStateArray(stringBuffer.toString());
                            applyRepository.save(apply);
                        }
                    }
                    dataObject.put("state", 0);
                    dataObject.put("message", "已拒绝申请!");

                    dailyLogService.save(new DailyLog("管理员[" + appUser.getUsername(),
                            "[拒绝申请]", "[成功]"));
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

    public ApplyRecord getAApplyRecord(Long id) {
        return applyRecordRepository.findOne(id);

    }

    public void delete(ApplyRecord applyRecord) {
        applyRecordRepository.delete(applyRecord);
    }


    public JSONArray getApplyRank() {
        List<Long> rankList = new ArrayList<>();
        List<ApplyRecord> applyRecordList = applyRecordRepository.findAll();
        for (ApplyRecord applyRecord : applyRecordList) {
            rankList.add(applyRecord.getAppUser().getId());
        }
        JSONArray data = new JSONArray();
        Set<Long> rankSet = new HashSet<>(rankList);
        for (Long id : rankSet) {
            AppUser appUser = appUserRepository.findOne(id);
            JSONObject userJson = new JSONObject();
            userJson.put("count", Collections.frequency(rankList, id));
            userJson.put("username", appUser.getUsername());
            userJson.put("department", appUser.getDepartment());
            userJson.put("schoolCode", appUser.getSchoolCode());
            userJson.put("phone", appUser.getPhone());
            data.add(userJson);
        }
        return data;

    }

}
