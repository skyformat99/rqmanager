package www.mjxy.rq.manager.constants;

/**
 * Created by wwhai on 2017/11/15.
 */
public enum FailureMessageEnum {

    EXPIRED(1, "账户已过期!"),
    CREDIT_FAILED(2, "认证失败!"),

    USER_LOCKED(3, "用户被冻结！"),
    LOGIN_FAILED(4, "登录失败!"),
    REGISTER_FAILED(5, "注册失败！"),
    USER_NOT_EXIST(6, "用户不存在！"),
    NO_ACTIVE(7, "账户没有激活！"),
    LOGIN_INVALID(8, "请登录后操作!"),
    LOGIN_ERROR(9, "登录出错!"),
    OPERATE_FAILED(10, "操作失败!"),
    REQUIRED_APIKEY(11, "没有APIKey"),
    FILE_DOWNLOAD_FAILED(12, "文件下载失败!"),
    INVALID_PARAM(13, "必填字段缺少！"),
    USER_ALREADY_EXIST(13, "用户已经存在，请不要重复添加！"),
    OPERATE_FAILURE(15, "操作失败！"),
    DEVICE_GROUP_NOT_EXIST(16, "分组不存在！"),
    DEVICE_NOT_EXIST(17, "设备不存在！"),
    EMPTY_DATA_SET(18, "数据集合为空!"),
    DEVICE_EXIST(19, "设备已存在!"),
    USER_DISABLED(20, "用户被锁定!"),
    USER_EXPIRED(21, "用户被锁定!"),
    //InternalAuthenticationServiceException

    NOT_AUTH(14, "请认证!");

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


    FailureMessageEnum(int code, String message) {
        this.code = code;
        this.message = message;

    }

    @Override
    public String toString() {
        return this.getCode() + " " + this.getMessage();

    }
}
