package cn.clxy.tools.swing.lua.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

    private String account;
    private String server;
    private String name;
    private List<Item> items = new ArrayList<Item>();

    public Character() {
    }

    public Character(String server, String name) {
        this.server = server;
        this.name = name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public String toString() {
        return name + "@" + server + ":" + account + "[" + items.toString() + "]";
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
