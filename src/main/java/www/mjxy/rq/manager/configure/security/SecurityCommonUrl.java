package www.mjxy.rq.manager.configure.security;

/**
 * Created by wwhai on 2018/2/23.
 */
public enum SecurityCommonUrl {
    DEFAULT_USER_OPERATE_PATH("注册入口", "/student/signUp"),
    DEFAULT_STATIC_PATH("默认静态资源的路径", "/static/**"),
    DEFAULT_AVATAR_PATH("默认头像的路径", "/avatar/**"),
    DEFAULT_TEST_PATH("默认测试接口", "/testLogin"),
    DEFAULT_INDEX_PATH("默认首页", "/");

    public static final String[] SWAGGER_UI_MATCHER = new String[]{
            "/v2/api-docs",
            "/docs.html",
            "/webjars/**"
    };
    private String name;
    private String url;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    SecurityCommonUrl(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return this.getUrl().toString();
    }


}
