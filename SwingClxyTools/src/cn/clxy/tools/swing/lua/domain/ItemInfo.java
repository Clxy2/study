package cn.clxy.tools.swing.lua.domain;

import java.awt.Image;
import java.io.Serializable;

import cn.clxy.tools.core.utils.BeanUtil;

public class ItemInfo implements Serializable {

    private String id;
    private String name;
    private transient Image image;
    private String imageName;
    private String tip;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String iconName) {
        this.imageName = iconName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return BeanUtil.equals(this, o, "id");
    }

    @Override
    public String toString() {
        return id + "-" + name;
    }

    @Override
    public int hashCode() {
        return BeanUtil.hashCode(id);
    }

    private static final long serialVersionUID = 1L;
}
