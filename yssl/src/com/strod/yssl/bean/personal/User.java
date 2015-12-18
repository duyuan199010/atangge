package com.strod.yssl.bean.personal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author lying
 */
@DatabaseTable(tableName = "user")
public class User implements Serializable{

    /**id*/
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "uid")
    private long uid;

    @DatabaseField(columnName = "userName")
    private String userName;

    @DatabaseField(columnName = "mobile")
    private String mobile;

    @DatabaseField(columnName = "headImg")
    private String headImg;

    @DatabaseField(columnName = "isLogin")
    private boolean isLogin;


    public User() {
    }

    public User(int id, long uid, String userName, String mobile, String headImg, boolean isLogin) {
        this.id = id;
        this.uid = uid;
        this.userName = userName;
        this.mobile = mobile;
        this.headImg = headImg;
        this.isLogin = isLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uid=" + uid +
                ", userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", headImg='" + headImg + '\'' +
                ", isLogin=" + isLogin +
                '}';
    }
}
