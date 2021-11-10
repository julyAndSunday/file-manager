package entity;


/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-05 22:05
 **/
public class HomeDto {
    private Home home;
    private User user;

    public Home getHome() {
        return home;
    }

    public User getUser() {
        return user;
    }

    public HomeDto(Home home, User user) {
        this.home = home;
        this.user = user;
    }
}
