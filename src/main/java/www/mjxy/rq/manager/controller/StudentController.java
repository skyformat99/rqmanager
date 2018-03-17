package www.mjxy.rq.manager.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.Apply;
import www.mjxy.rq.manager.model.ApplyRecord;
import www.mjxy.rq.manager.model.Room;
import www.mjxy.rq.manager.service.AppUserService;
import www.mjxy.rq.manager.service.ApplyRecordService;
import www.mjxy.rq.manager.service.ApplyService;
import www.mjxy.rq.manager.service.RoomService;
import www.mjxy.rq.manager.utils.ReturnResult;

import java.text.SimpleDateFormat;

/**
 * Created by wwhai on 2018/2/23.
 */
@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    ApplyService applyService;
    @Autowired
    AppUserService appUserService;

    @Autowired
    ApplyRecordService applyRecordService;
    @Autowired
    RoomService roomService;

    /**
     * 申请教室
     *
     * @param applyJsonBody
     * @return
     */

    @RequestMapping(value = "/applyRoom", method = RequestMethod.POST)
    public JSONObject applyRoom(@RequestBody JSONObject applyJsonBody) {
        JSONObject returnJson = new JSONObject();

        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String reason = applyJsonBody.getString("reason");
        Long roomId = applyJsonBody.getLong("roomId");
        String applyType = applyJsonBody.getString("applyType");
        String applyDateString = applyJsonBody.getString("applyDateString");
        System.out.println(applyType);

        //排除null
        if ((reason == null || roomId == null || applyType == null || applyDateString == null)) {
            return ReturnResult.returnResult(0, "参数不完整!");
        }

        Room room = roomService.getByRoomId(roomId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (room != null) {
            //判断是否能申请
            try {

                Apply apply = applyService.getAApplyByDate(simpleDateFormat.parse(applyDateString));

                if (apply != null) {
                    char stateArray[] = apply.getStateArray().toCharArray();
                    char applyTypeArray[] = applyType.toCharArray();

                    for (int i = 0; i < applyType.toCharArray().length; i++) {
                        if (applyTypeArray[i] == '1')/*1表示这个位需要申请*/ {
                            if (stateArray[i] == '0') {//0表示空闲
                                stateArray[i] = '1';//表示提交后待定
                                apply.setStateArray(new String(stateArray));
                                applyService.createApply(apply);

                                ApplyRecord applyRecord = new ApplyRecord();
                                applyRecord.setAppUser(appUser);
                                applyRecord.setRoom(room);
                                applyRecord.setApply(apply);
                                applyRecord.setState(1);
                                applyRecord.setApplyDate(simpleDateFormat.parse(applyDateString));
                                applyRecord.setReason(reason);
                                applyRecordService.save(applyRecord);


                                returnJson.put("state", 1);
                                returnJson.put("message", "申请成功，请等待!");
                                System.out.println("日志在这里输出");
                            } else {
                                returnJson.put("state", 1);
                                returnJson.put("message", "目前被占用暂时不能申请!");
                            }
                        }
                    }

                } else {
                    //么有被人申请就首创一个
                    Apply newApply = new Apply();
                    newApply.setStateArray(new String(applyType.toCharArray()));
                    newApply.setApplyDate(simpleDateFormat.parse(applyDateString));
                    applyService.createApply(newApply);
                    ApplyRecord applyRecord = new ApplyRecord();
                    applyRecord.setAppUser(appUser);
                    applyRecord.setRoom(room);
                    applyRecord.setState(1);
                    applyRecord.setApply(newApply);
                    applyRecord.setApplyDate(simpleDateFormat.parse(applyDateString));
                    applyRecord.setReason(reason);
                    applyRecordService.save(applyRecord);
                    returnJson.put("state", 1);
                    returnJson.put("message", "申请成功，请等待!");
                    System.out.println("日志在这里输出");
                }

            } catch (Exception e) {
                e.printStackTrace();
                returnJson.put("state", 0);
                returnJson.put("message", "日期解析错误!");
            }

        } else {
            returnJson.put("state", 0);
            returnJson.put("message", "教室不存在!");
        }
        return returnJson;

    }


    /**
     * 查看我的申请记录
     */

    @RequestMapping(value = "/applies/{pageNumber}/{size}")
    public JSONObject applies(@PathVariable Integer pageNumber, @PathVariable Integer size) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (pageNumber == null || size == null || (pageNumber == null && size == null)) {
            pageNumber = 0;
            size = 20;
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 1);
        jsonObject.put("data", applyRecordService.getRecordByAppUser(appUser));
        jsonObject.put("message", "查询成功!");
        return jsonObject;


    }

    /**
     * 取消一次申请
     *
     * @param jsonBody
     * @return
     */

    @RequestMapping(value = "/cancelApply", method = RequestMethod.POST)
    public JSONObject cancelApply(@RequestBody JSONObject jsonBody) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject jsonObject = new JSONObject();


        return jsonObject;
    }


}