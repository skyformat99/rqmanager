package www.mjxy.rq.manager.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import www.mjxy.rq.manager.dao.ApplyRepository;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.Apply;
import www.mjxy.rq.manager.model.Room;

import java.util.List;

/**
 * Created by wwhai on 2018/2/24.
 */
@Service
public class ApplyService {
    @Autowired
    ApplyRepository applyRepository;
    @Autowired
    RoomService roomService;

    public Apply createApply(Apply apply) {
        return applyRepository.save(apply);
    }

    public JSONObject getAppliesByPage(AppUser appUser, Integer pageNumber, Integer size) {
        PageRequest pageRequest = new PageRequest(pageNumber, size, new Sort(Sort.Direction.DESC, "id"));
        Page<Apply> applyPage = applyRepository.findAllByAppUser(appUser, pageRequest);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", loopList(applyPage.getContent()));
        jsonObject.put("page", pageNumber);
        jsonObject.put("isLast", applyPage.isLast());
        jsonObject.put("isFirst", applyPage.isFirst());
        jsonObject.put("totalPage", applyPage.getTotalPages());
        return jsonObject;


    }

    public Boolean isAlreadyApplyRoom(Room room, AppUser appUser) {
        List<Apply> applyList = applyRepository.findAllByRoomAndAppUser(room, appUser);

        return applyList != null && applyList.size() != 0 ? true : false;

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
            dataObject.put("reason", apply.getReason());
            dataObject.put("create_time", apply.getCreateTime());
            dataObject.put("state", apply.getState());
            JSONObject applierInfo = new JSONObject();
            applierInfo.put("username", apply.getAppUser().getUsername());
            applierInfo.put("schoolCode", apply.getAppUser().getSchoolCode());
            dataObject.put("applierInfo", applierInfo);
            jsonArray.add(dataObject);
        }
        return jsonArray;
    }

    /**
     * 处理请求
     * 通过
     * 拒绝
     */
    public JSONObject processApply(JSONObject body) {
        Long applyId = body.getLongValue("applyId");
        String processSign = body.getString("processSign");
        JSONObject dataObject = new JSONObject();
        Apply apply = applyRepository.findOne(applyId);
        switch (processSign) {
            case "ACCESS":
                //state更新为 2 已通过

                if (apply != null) {
                    //通过
                    apply.setState(2);
                    Room room = apply.getRoom();
                    //同时 房间标识为：已经借出
                    room.setState(2);
                    roomService.save(room);
                    applyRepository.save(apply);
                    dataObject.put("state", 1);
                    dataObject.put("message", "已经同意该申请!");
                    return dataObject;
                } else {
                    dataObject.put("state", 0);
                    dataObject.put("message", "申请不存在!");
                }

                break;
            case "DENY":
                //state更新为 0 拒绝
                if (apply != null) {
                    //拒绝
                    apply.setState(0);
                    Room room = apply.getRoom();
                    //同时 房间标识为：可以借0
                    room.setState(0);
                    roomService.save(room);
                    applyRepository.save(apply);
                    dataObject.put("state", 1);
                    dataObject.put("message", "已经拒绝该申请!");
                } else {

                    dataObject.put("state", 0);
                    dataObject.put("message", "申请不存在!");
                }
                break;
            default:
                dataObject.put("state", 0);
                dataObject.put("message", "操作标记只有ACCESS和DENY两种!");
                break;
        }
        return dataObject;
    }

}
