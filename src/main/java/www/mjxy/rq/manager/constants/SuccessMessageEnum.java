package www.mjxy.rq.manager.constants;

/**
 * Created by wwhai on 2017/11/15.
 */
public enum SuccessMessageEnum {
    LOGIN_SUCCESS(1, "登录成功!"),
    REGISTER_SUCCESS(2, "登录成功!"),
    LOG_OUT_SUCCESS(3, "注销成功!"),
    OPERATE_SUCCESS(4, "查询成功"),
    DEVICE_ADD_SUCCESS(5, "查询成功"),
    DEVICE_GROUP_ADD_SUCCESS(6, "查询成功"),
    DEVICE_GROUP_EXIST(7, "分组已经存在"),
    DEVICE_GROUP_DELETE_SUCCESS(8, "查询成功");


    private int code;
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    private SuccessMessageEnum(int code, String message) {
        this.code = code;
        this.message = message;

    }

    @Override
    public String toString() {
        return super.toString();

    }
}
