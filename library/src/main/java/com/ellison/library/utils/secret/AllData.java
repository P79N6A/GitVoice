package com.ellison.library.utils.secret;

/**
 * Created by Ellison on 2017/10/10.
 * @author Ellison
 */

public class AllData {

    private String state;
    private String data;
    private String message;

    @Override
    public String toString() {
        return "{" +
                "state='" + state + '\'' +
                ", data='" + data + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
