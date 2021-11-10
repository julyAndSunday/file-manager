package entity;

import java.nio.channels.SocketChannel;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-10 20:04
 **/
public class CreateHomeResponse {
    private FutureTask<SocketChannel> futureTask;
    private Integer homeId;

    public CreateHomeResponse(FutureTask<SocketChannel> futureTask, Integer homeId) {
        this.futureTask = futureTask;
        this.homeId = homeId;
    }

    public FutureTask<SocketChannel> getFutureTask() {
        return futureTask;
    }

    public Integer getHomeId() {
        return homeId;
    }
}
