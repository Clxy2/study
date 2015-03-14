package cn.clxy.tools.swing.lua.domain;

import cn.clxy.tools.core.utils.StringUtil;

public class Filter {

    private String[] itemPatterns = new String[] {};
    private String[] charaPatterns = new String[] {};

    public Filter(String itemNames, String charaNames) {

        if (!StringUtil.isBlank(itemNames)) {
            this.itemPatterns = itemNames.split("\\s+");
        }

        if (!StringUtil.isBlank(charaNames)) {
            this.charaPatterns = charaNames.split("\\s+");
        }
    }

    public boolean doFilter(ItemRow item) {

        if (item == null) {
            return false;
        }

        String itemName = item.getName();
        for (String p : itemPatterns) {
            if (itemName.indexOf(p) >= 0)
                return true;
        }

        String detail = item.getDetail();
        for (String p : charaPatterns) {
            if (detail.indexOf(p) >= 0)
                return true;
        }

        return false;
    }
}
