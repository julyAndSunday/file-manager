package service;

import com.alibaba.fastjson.JSONObject;
import common.R;
import entity.CreateHomeResponse;
import entity.Home;
import entity.HomeDto;
import entity.User;
import p2p.P2PClient;
import utils.HttpUtil;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-06 09:35
 **/
public class HomeService {
    private String ip;
    private int port;
    private final String CREATE = "/home/create";
    private final String JOIN = "/home/join";
    private P2PClient p2PClient;

    public HomeService(String ip, int port) {
        this.ip = ip;
        this.port = port;
        p2PClient = new P2PClient();
    }

    //socket的future和通道id
    public CreateHomeResponse createHome(String username, String homePassword) {
        User user = new User(username);
        Home home = new Home(homePassword);
        HomeDto homeDto = new HomeDto(home, user);

        String url = buildUrl(CREATE);
        String response = HttpUtil.post(url, homeDto);
        FutureTask<SocketChannel> futureTask = p2PClient.connect(home.getId());
        CreateHomeResponse createHomeResponse = new CreateHomeResponse(futureTask, home.getId());
        return createHomeResponse;
    }

    public FutureTask<SocketChannel> joinHome(String homeContent) {
        String[] content = homeContent.split(":");
        User user = new User();
        Home home = new Home(Integer.parseInt(content[0]), content[1]);
        HomeDto homeDto = new HomeDto(home, user);
        String url = buildUrl(JOIN);
        String response = HttpUtil.post(url, homeDto);
        R r = JSONObject.parseObject(response, R.class);
        Integer homeId = (Integer) r.getData();
        return p2PClient.connect(homeId);
    }

    private String buildUrl(String path) {
        return "http://" + ip + ":" + port + "/" + path;
    }
}
