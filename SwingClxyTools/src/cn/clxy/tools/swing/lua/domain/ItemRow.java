package cn.clxy.tools.swing.lua.domain;

import java.awt.Image;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * for display.
 * @author clxy
 */
public class ItemRow {

    private Item item = null;
    private Map<String, Item> map = new TreeMap<String, Item>();

    public void addItem(String name, Item item) {

        if (this.item == null) {
            this.item = item.copy();
            map.put(name, item);
            return;
        }

        this.item.add(item.getCount());

        Item i = map.get(name);
        if (i != null) {
            i.add(item.getCount());
            return;
        }

        map.put(name, item);
    }

    public String getDetail() {

        StringBuilder sb = new StringBuilder();
        int l = 0;
        for (Entry<String, Item> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(':');
            sb.append(getCount(entry.getValue()));
            sb.append(' ');
            l++;
            if (l % 5 == 0) {
                sb.append("\r\n");
            }
        }
        return sb.toString();
    }

    public Integer getCount() {
        return getCount(item);
    }

    public void setInfo(ItemInfo ii) {
        item.setInfo(ii);
    }

    private static Integer getCount(Item item) {

        Integer count = item.getCount();
        if (Item.ID_GOLD.equals(item.getId())) {
            return count / 10000;
        }

        return count;
    }

    public String getId() {
        return item.getId();
    }

    public String getName() {
        return item.getName();
    }

    public Image getIcon() {
        return item.getImage();
    }

    private static final long serialVersionUID = 1L;
}
