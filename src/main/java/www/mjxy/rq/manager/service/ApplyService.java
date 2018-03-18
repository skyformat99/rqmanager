package www.mjxy.rq.manager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.ApplyRecordRepository;
import www.mjxy.rq.manager.dao.ApplyRepository;
import www.mjxy.rq.manager.model.Apply;
import www.mjxy.rq.manager.model.ApplyRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by wwhai on 2018/2/24.
 */
@Service
public class ApplyService {
    @Autowired
    ApplyRecordService applyRecordService;
    @Autowired
    ApplyRecordRepository applyRecordRepository;
    @Autowired
    ApplyRepository applyRepository;
    @Autowired
    RoomService roomService;

    public Apply createApply(Apply apply) {
        return applyRepository.save(apply);
    }


    public JSONObject getAllApplies(Integer pageNumber, Integer size) {
        PageRequest pageRequest = new PageRequest(pageNumber, size, new Sort(Sort.Direction.DESC, "id"));
        Page<Apply> applyPage = applyRepository.findAll(pageRequest);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", loopList(applyPage.getContent()));
        jsonObject.put("page", pageNumber);
        jsonObject.put("isLast", applyPage.isLast());
        jsonObject.put("isFirst", applyPage.isFirst());
        jsonObject.put("totalPage", applyPage.getTotalPages());
        return jsonObject;


    }

    public JSONArray loopList(List<Apply> list) {
        JSONArray jsonArray = new JSONArray();
        for (Apply apply : list) {
            JSONObject dataObject = new JSONObject();
            dataObject.put("id", apply.getId());
            dataObject.put("create_time", apply.getCreateTime());
            dataObject.put("state", apply.getStateArray());
            JSONObject applierInfo = new JSONObject();
            dataObject.put("applierInfo", applierInfo);
            jsonArray.add(dataObject);
        }
        return jsonArray;
    }




    public void deleteApply(Long id) {
        applyRepository.delete(id);
    }

    public Apply getAApplyByDate(Date date) {
        return applyRepository.findTopByApplyDate(date);
    }

}
