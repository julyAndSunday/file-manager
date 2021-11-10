package common;


/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-05 22:06
 **/
public class R {
    private int code;
    private String message;
    private Object data;

    public R(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static R success(Object data) {
        return new R(200,"success",data);
    }

    public static R failed(Object data) {
        return new R(500,"failed",data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
