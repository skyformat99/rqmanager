package www.mjxy.rq.manager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.DailyLogRepository;
import www.mjxy.rq.manager.model.DailyLog;

import java.util.List;

/**
 * Created by wwhai on 2018/3/18.
 */
@Service(value = "DailyLogService")
public class DailyLogService {
    @Autowired
    DailyLogRepository dailyLogRepository;

    public void save(DailyLog dailyLog) {
        dailyLogRepository.save(dailyLog);
    }

    public JSONArray getAll() {
        JSONArray data = new JSONArray();
        List<DailyLog> dailyLogList = dailyLogRepository.findAll();
        for (DailyLog log : dailyLogList) {
            JSONObject logJson = new JSONObject();
            logJson.put("who", log.getWho());
            logJson.put("what", log.getDoWhat());
            logJson.put("happend", log.getHappend());
            data.add(logJson);
        }
        return data;

    }
}
