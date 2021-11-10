package entity;


import java.util.Date;
import java.util.UUID;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-05 21:44
 **/
public class Home {
    private Integer id;
    private String password;
    private Date endTime;
    public Home(String password) {
        this.id = Math.abs(UUID.randomUUID().hashCode());
        this.password = password;
    }

    public Home(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    public Home(Integer id, String password,Date endTime) {
        this.id = id;
        this.password = password;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}

