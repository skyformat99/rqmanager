package www.mjxy.rq.manager.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import www.mjxy.rq.manager.constants.TimeType;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.Apply;
import www.mjxy.rq.manager.model.Room;
import www.mjxy.rq.manager.service.AppUserService;
import www.mjxy.rq.manager.service.ApplyService;
import www.mjxy.rq.manager.service.RoomService;
import www.mjxy.rq.manager.service.UserRoleService;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    UserRoleService userRoleService;
    @Autowired
    RoomService roomService;

    /**
     * 注册
     *
     * @param loginParamMap
     * @return
     */


    /**
     * 下面是申请 查看等操作
     */

    @RequestMapping(value = "/applyRoom", method = RequestMethod.POST)
    public JSONObject applyRoom(@RequestBody JSONObject applyJsonBody) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject returnJson = new JSONObject();
        String reason = applyJsonBody.getString("reason");
        Long roomId = applyJsonBody.getLong("roomId");
        TimeType timeType = (TimeType) applyJsonBody.get("timeType");
        String applyDateString = applyJsonBody.getString("applyDateString");

        //排除null
        if ((reason == null || roomId == null || timeType == null || applyDateString == null)) {
            returnJson.put("state", 0);
            returnJson.put("message", "参数不完整");
            return returnJson;
        }

        Room room = roomService.getByRoomId(roomId);
        if (room != null) {
            //判断是否能申请
            /**
             * 判断 XXX时间类型下 XX-XX时间段  是否存在 UID为UID RID为RID的申请
             */

            Date startDate = new Date();
            Date endDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
            try {
                switch (timeType) {
                    //设置时间类型
                    case AM_8_10:
                        startDate = simpleDateFormat.parse(applyDateString + "-" + 8);
                        endDate = simpleDateFormat.parse(applyDateString + "-" + 10);
                        break;
                    case AM_10_12:
                        startDate = simpleDateFormat.parse(applyDateString + "-" + 10);
                        endDate = simpleDateFormat.parse(applyDateString + "-" + 12);
                        break;
                    case AM_12_14:
                        startDate = simpleDateFormat.parse(applyDateString + "-" + 12);
                        endDate = simpleDateFormat.parse(applyDateString + "-" + 14);
                        break;
                    case PM_14_16:
                        startDate = simpleDateFormat.parse(applyDateString + "-" + 14);
                        endDate = simpleDateFormat.parse(applyDateString + "-" + 16);
                        break;
                    case PM_16_21:
                        startDate = simpleDateFormat.parse(applyDateString + "-" + 16);
                        endDate = simpleDateFormat.parse(applyDateString + "-" + 21);
                        break;
                    case TWO_DAYS:
                        startDate = simpleDateFormat.parse(applyDateString + "-" + 8);
                        endDate = simpleDateFormat.parse(applyDateString + "-" + 10);
                        break;
                    case THREE_DAYS:
                        startDate = simpleDateFormat.parse(applyDateString + "-" + 8);
                        endDate = simpleDateFormat.parse(applyDateString + "-" + 10);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                //
                returnJson.put("state", 0);
                returnJson.put("message", "时间格式错误，应该是:yyyy-MM-dd-HH");
                return returnJson;

            }
            System.out.println("开始时间:" + startDate);
            System.out.println("结束时间:" + endDate);

            if (applyService.isRoomAtFreeStateInThisDate(startDate, endDate)) {
                returnJson.put("state", 0);
                returnJson.put("message", "已经被占用！请等待其他时刻!");
                return returnJson;
            }
            //业务开始 timeType 申请的类型，是前端提交的一个字符串 后端自动处理成时间
            Apply apply = new Apply();
            apply.setAppUser(appUser);
            apply.setRoom(room);
            apply.setReason(reason);
            //设置申请时间
            //时间+申请类型 = 完整日期
            try {
                apply.setApplyDate(new SimpleDateFormat("yyyy-MM-dd").parse(applyDateString));
            } catch (Exception e) {
                returnJson.put("state", 0);
                returnJson.put("message", "时间格式错误，应该是:yyyy-MM-dd");
                return returnJson;

            }
            switch (timeType) {
                //设置时间类型
                case AM_8_10:
                    apply.setTimeType(TimeType.AM_8_10);
                    break;
                case AM_10_12:
                    apply.setTimeType(TimeType.AM_10_12);
                    break;
                case AM_12_14:
                    apply.setTimeType(TimeType.AM_12_14);
                    break;
                case PM_14_16:
                    apply.setTimeType(TimeType.PM_14_16);
                    break;
                case PM_16_21:
                    apply.setTimeType(TimeType.PM_16_21);
                    break;
                case TWO_DAYS:
                    apply.setTimeType(TimeType.TWO_DAYS);
                    break;
                case THREE_DAYS:
                    apply.setTimeType(TimeType.THREE_DAYS);
                    break;
                default:
                    break;
            }
            applyService.createApply(apply);
            returnJson.put("state", 1);
            returnJson.put("message", "申请成功!请等待通知!");
        }

        return returnJson;

    }


    /**
     * 查看我的申请记录
     */

    @RequestMapping(value = "/applies", method = RequestMethod.POST)
    public JSONObject applies(@RequestBody JSONObject pageJsonBody) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer pageNumber = pageJsonBody.getIntValue("pageNumber");
        Integer size = pageJsonBody.getIntValue("size");
        JSONObject data = applyService.getAppliesByPage(appUser, pageNumber, size);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 1);
        jsonObject.put("data", data);
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