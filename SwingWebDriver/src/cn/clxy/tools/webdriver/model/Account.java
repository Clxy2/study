package cn.clxy.tools.webdriver.model;

import java.io.File;
import java.io.Serializable;

public class Account implements Serializable {

    private String cid;
    private File local;
    private String name;
    private String userid;
    private String password;

    private String url;
    private String username;

    public boolean isAnonymous() {
        return userid == null || password == null;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public File getLocal() {
        return local;
    }

    public void setLocal(File local) {
        this.local = local;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private static final long serialVersionUID = 1L;
}
