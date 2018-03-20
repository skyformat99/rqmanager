package www.mjxy.rq.manager.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import www.mjxy.rq.manager.constants.FailureMessageEnum;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.DailyLog;
import www.mjxy.rq.manager.model.Room;
import www.mjxy.rq.manager.model.UserRole;
import www.mjxy.rq.manager.service.*;
import www.mjxy.rq.manager.utils.MD5Generator;
import www.mjxy.rq.manager.utils.ReturnResult;

/**
 * Created by wwhai on 2018/2/23.
 */
@RestController
@PreAuthorize(value = "hasRole('ROLE_ADMIN') AND hasRole('ROLE_USER')")
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    DailyLogService dailyLogService;
    @Autowired
    ApplyService applyService;
    @Autowired
    RoomService roomService;

    @Autowired
    ApplyRecordService applyRecordService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    UserRoleService userRoleService;


    /**
     * 增加一间教室
     *
     * @return
     */

    @RequestMapping(value = "/postRoom")
    public JSONObject postRoom(@RequestBody JSONObject roomJsonBody) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String roomName = roomJsonBody.getString("roomName");
        String roomNumber = roomJsonBody.getString("roomNumber");
        String roomInfo = roomJsonBody.getString("roomInfo");
        String location = roomJsonBody.getString("location");
        if (roomInfo == null || roomName == null || roomNumber == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", 0);
            jsonObject.put("message", "请输入有效的参数");
            return jsonObject;
        } else {
            Room room = new Room();
            room.setRoomInfo(roomInfo);
            room.setRoomName(roomName);
            room.setRoomNumber(roomNumber);
            room.setLocation(location);
            roomService.createRoom(room);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", 1);
            jsonObject.put("message", "添加成功!");


            dailyLogService.save(new DailyLog("管理员[" + appUser.getUsername(),
                    "][增加教室][" +
                            room.getRoomName() + "]",
                    "[成功]"));
            return jsonObject;

        }
    }

    @RequestMapping(value = "/applies")
    public JSONObject applies() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 1);
        jsonObject.put("data", applyRecordService.getAllApplyRecord());
        jsonObject.put("message", "查询成功!");
        return jsonObject;


    }

    /**
     * 处理请求
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "/processApply", method = RequestMethod.POST)
    public JSONObject processApply(@RequestBody JSONObject body) {
        Long applyRecordId = body.getLongValue("applyRecordId");
        String processSign = body.getString("processSign");
        if (applyRecordId == null || processSign == null) {
            return ReturnResult.returnResult(0, "参数不完整!");
        }

        return applyRecordService.processApply(applyRecordId, processSign);
    }

    /**
     * 删除一间教室
     *
     * @param body
     * @return
     */

    @RequestMapping(value = "/deleteRoom", method = RequestMethod.POST)
    public JSONObject deleteRoom(@RequestBody JSONObject body) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        JSONObject jsonObject = new JSONObject();
        Long roomId = body.getLongValue("roomId");
        Room room = roomService.getByRoomId(roomId);
        if (room != null) {
            roomService.deleteRoom(room);
            jsonObject.put("state", 0);
            jsonObject.put("message", "教室删除成功!");
            dailyLogService.save(new DailyLog("管理员[" + appUser.getUsername(),
                    "][删除教室][" +
                            room.getRoomName() + "]",
                    "[成功]"));

        } else {
            jsonObject.put("state", 0);
            jsonObject.put("message", "教室不存在，删除失败!");
        }
        return jsonObject;

    }


    /**
     * 更新教室
     *
     * @param roomJsonBody
     * @return
     */

    @RequestMapping(value = "/updateRoom", method = RequestMethod.POST)
    public JSONObject updateRoom(@RequestBody JSONObject roomJsonBody) {
        JSONObject jsonObject = new JSONObject();
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long roomId = roomJsonBody.getLongValue("roomId");
        String roomName = roomJsonBody.getString("roomName");
        String roomNumber = roomJsonBody.getString("roomNumber");
        String roomInfo = roomJsonBody.getString("roomInfo");
        String location = roomJsonBody.getString("location");
        if (roomInfo == null || roomName == null || roomNumber == null) {
            jsonObject.put("state", 0);
            jsonObject.put("message", "请输入有效的参数");
            return jsonObject;
        } else {
            Room room = roomService.getByRoomId(roomId);
            if (room != null) {
                room.setRoomInfo(roomInfo);
                room.setRoomName(roomName);
                room.setRoomNumber(roomNumber);
                room.setLocation(location);
                roomService.save(room);
                jsonObject.put("state", 1);
                jsonObject.put("message", "更新成功!");
                dailyLogService.save(new DailyLog("管理员[" + appUser.getUsername(),
                        "][更新教室][" +
                                room.getRoomName() + "]",
                        "[成功]"));

                return jsonObject;
            } else {
                jsonObject.put("state", 0);
                jsonObject.put("message", "教室不存在，删除失败!");
                return jsonObject;
            }

        }

    }

    /**
     * 管理员增加一个用户
     *
     * @param loginParamMap
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public JSONObject addUser(@RequestBody JSONObject loginParamMap) {
        AppUser admin = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        JSONObject resultJson = new JSONObject();
        /**
         * Map 提取参数的时候可能会抛出异常，所以进行异常捕获
         */
        String username = loginParamMap.getString("username");
        String email = loginParamMap.getString("email");
        String phone = loginParamMap.getString("phone");
        String schoolCode = loginParamMap.getString("schoolCode");
        String trueName = loginParamMap.getString("trueName");
        String department = loginParamMap.getString("department");


        /**
         * 排除非空
         */
        if (schoolCode.equals("") || department.equals("") || username.equals("") || trueName.equals("") || email.equals("") || phone.equals("")) {
            resultJson.put("state", 0);
            resultJson.put("message", FailureMessageEnum.INVALID_PARAM);
            return resultJson;
        } else {
            /**
             *  判断用户是否存在
             */

            if (appUserService.isUsernameExist(username)) {
                resultJson.put("state", 0);
                resultJson.put("message", FailureMessageEnum.USER_ALREADY_EXIST.getMessage());

                /**
                 * 所有的非法条件过滤以后，进行增加用户
                 */
            } else {
                AppUser appUser = new AppUser();
                //系统自己生成密码
                appUser.setUsername(username);
                appUser.setPassword(MD5Generator.EncodingMD5("66666666"));
                appUser.setPhone(phone);
                appUser.setEmail(email);
                 appUser.setTrueName(trueName);
                appUser.setDepartment(department);
                appUserService.createUser(appUser);
                UserRole userRole = new UserRole();
                userRole.setAppUser(appUser);
                userRole.setRole("ROLE_USER");
                //默认为USER角色
                userRoleService.createUserRole(userRole);
                resultJson.put("state", 1);
                resultJson.put("message", "添加用户成功!");
                dailyLogService.save(new DailyLog("管理员[" + admin.getUsername(),
                        "][添加用户教室][" +
                                appUser.getTrueName() + "]",
                        "[成功]"));
            }

        }
        return resultJson;
    }

    /**
     * 获取log日志
     *
     * @return
     */

    @RequestMapping(value = "/getAllLog")
    public JSONObject getAllLog() {
        return ReturnResult.returnResultWithData(1, "获取成功!", dailyLogService.getAll());
    }

    /**
     * 获取所有的用户
     *
     * @return
     */
    @RequestMapping(value = "/getAllUser")
    public JSONObject getAllUser() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("state", 1);
        resultJson.put("data", appUserService.getAllUserApplyData());
        resultJson.put("message", "查询成功!");
        return resultJson;

    }

    /**
     * 申请次数排行榜
     *
     * @return
     */
    @RequestMapping(value = "/applyRank")

    public JSONObject applyRank() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("state", 1);
        resultJson.put("data", applyRecordService.getApplyRank());
        resultJson.put("message", "查询成功!");
        return resultJson;


    }


}
