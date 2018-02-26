package www.mjxy.rq.manager.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import www.mjxy.rq.manager.constants.FailureMessageEnum;
import www.mjxy.rq.manager.constants.SuccessMessageEnum;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.model.Apply;
import www.mjxy.rq.manager.model.Room;
import www.mjxy.rq.manager.model.UserRole;
import www.mjxy.rq.manager.service.AppUserService;
import www.mjxy.rq.manager.service.ApplyService;
import www.mjxy.rq.manager.service.RoomService;
import www.mjxy.rq.manager.service.UserRoleService;
import www.mjxy.rq.manager.utils.MD5Generator;

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

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public JSONObject addUser(@RequestBody(required = false) JSONObject loginParamMap) {
        JSONObject resultJson = new JSONObject();
        /**
         * Map 提取参数的时候可能会抛出异常，所以进行异常捕获
         */


        String username = loginParamMap.getString("username");
        String password = loginParamMap.getString("password");
        String email = loginParamMap.getString("email");
        String phone = loginParamMap.getString("phone");
        String schoolCode = loginParamMap.getString("schoolCode");

        /**
         * 排除非空
         */
        if (username.equals("") || password.equals("") || email.equals("") || phone.equals("")) {
            resultJson.put("state", 0);
            resultJson.put("message", FailureMessageEnum.INVALID_PARAM);
            return resultJson;
        } else {
            /**
             *  判断用户是否存在
             */

            if (appUserService.isUserExistsByParameter(username, schoolCode)) {
                resultJson.put("state", 0);
                resultJson.put("message", FailureMessageEnum.USER_ALREADY_EXIST.getMessage());

                /**
                 * 所有的非法条件过滤以后，进行增加用户
                 */
            } else {
                AppUser appUser = new AppUser();
                appUser.setUsername(username);
                appUser.setPassword(MD5Generator.EncodingMD5(password));
                appUser.setPhone(phone);
                appUser.setEmail(email);
                appUser.setSchoolCode(schoolCode);
                appUserService.createUser(appUser);
                UserRole userRole = new UserRole();
                userRole.setAppUser(appUser);
                userRole.setRole("ROLE_USER");
                //默认为USER角色
                userRoleService.createUserRole(userRole);
                resultJson.put("state", 1);
                resultJson.put("message", SuccessMessageEnum.REGISTER_SUCCESS);
            }

        }
        return resultJson;
    }

    /**
     * 下面是申请 查看等操作
     */

    @RequestMapping(value = "/applyRoom", method = RequestMethod.POST)
    public JSONObject applyRoom(@RequestBody JSONObject applyJsonBody) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String reason = applyJsonBody.getString("reason");
        Long roomId = applyJsonBody.getLong("roomId");
        if (reason == null || roomId == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", 0);
            jsonObject.put("message", "请检查输入参数的完整性!");
            return jsonObject;
        }

        if (roomService.isRoomExists(roomId)) {


            /**
             * 判断  是否已经申请过了
             * 判断依据是：数据库是否存在
             * XX 用户 XXRoomId的记录
             */
            if (applyService.isAlreadyApplyRoom(roomService.getByRoomId(roomId), appUser)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("state", 0);
                jsonObject.put("message", "你已经申请过该教室了!请不要重复申请!");
                return jsonObject;
            } else {
                Apply apply = new Apply();
                apply.setAppUser(appUser);
                apply.setReason(reason);
                Room room = roomService.getByRoomId(roomId);
                room.setState(1);
                apply.setRoom(room);
                //1 修改room的状态
                //2 修改apply的状态
                roomService.save(room);
                applyService.createApply(apply);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("state", 1);
                jsonObject.put("message", "申请成功!请等待审核!");
                return jsonObject;
            }


        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", 0);
            jsonObject.put("message", "房间ID不存在!请检查输入参数!");
            return jsonObject;
        }


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



}