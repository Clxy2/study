package cn.clxy.tools.swing.lua.domain;

import java.io.Serializable;

public class Item extends ItemInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ID_GOLD = "g";

    private Integer count = 1;

    public Item() {
    }

    public Item(String id) {
        setId(id);
    }

    public Item(String id, String name, Integer count) {
        setId(id);
        setName(name);
        this.count = count;
    }

    public Item copy() {

        Item item = new Item(getId(), getName(), count);
        item.setImage(getImage());
        item.setTip(getTip());
        return item;
    }

    public void add(Integer count) {
        this.count += count;
    }

    public void setInfo(ItemInfo ii) {
        setName(ii.getName());
        setImage(ii.getImage());
        setTip(ii.getTip());
    }

    @Override
    public String toString() {
        return super.toString() + "," + count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
