package cn.clxy.tools.swing.lua.service;

import java.util.List;

import cn.clxy.tools.core.Messagable;
import cn.clxy.tools.core.Stoppable;
import cn.clxy.tools.swing.lua.domain.Character;
import cn.clxy.tools.swing.lua.service.impl.ItemInfoServiceImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(ItemInfoServiceImpl.class)
public interface ItemInfoService extends Stoppable, Messagable {

    void setItemInfo(final List<Character> characters);
}
