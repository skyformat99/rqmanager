package www.mjxy.rq.manager.constants;

/**
 * Created by wwhai on 2017/11/25.
 * 这里暂时封装了一个本地的EMQ的HTTP接口
 */
public enum LocalHostEMQHttpApiUrlEnum {
    /**
     * curl -v --basic -u <user>:<passwd> -k
     * http://localhost:8080/api/v2/nodes/emq@127.0.0.1/clients
     */
    LOCALHOST_BASIC_AUTH_URL("本地的BASIC认证", "http://localhost:8080/api/v2/nodes/emq@127.0.0.1/clients"),

    /**
     * GET
     * api/v2/management/nodes
     * 获取全部节点的基本信息
     *
     * @return
     */
    ALL_NODES_INFORMATION_URL("获取全部节点的基本信息", "http://localhost:8080/api/v2/management/nodes"),


    /**
     * GET
     * api/v2/management/nodes/emq@127.0.0.1
     * 获取指定节点的基本信息
     */
    NODE_INFOMATION_URL("获取全部节点的基本信息", "http://localhost:8080/api/v2/management/nodes/emq@127.0.0.1");

    private String name;
    private String url;

    LocalHostEMQHttpApiUrlEnum(String name, String url) {
        this.name = name;
        this.url = url;

    }


    /**
     * GET
     * api/v2/monitoring/nodes
     * 获取全部节点的监控数据
     */


    /**
     * GET
     * api/v2/nodes/emq@127.0.0.1/clients
     * 获取指定节点的客户端连接列表
     */


    /**
     * GET
     * api/v2/nodes/emq@127.0.0.1/clients/{clientid}
     * 获取节点指定客户端连接的信息
     */


    /**
     * GET
     * api/v2/clients/{clientid}
     * 获取集群内指定客户端的信息
     */


}
