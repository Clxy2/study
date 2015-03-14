package cn.clxy.tools.swing.lua.service;

import java.util.List;

import cn.clxy.tools.core.Messagable;
import cn.clxy.tools.core.Stoppable;
import cn.clxy.tools.swing.lua.domain.Character;
import cn.clxy.tools.swing.lua.service.impl.ItemServiceImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(ItemServiceImpl.class)
public interface ItemService extends Stoppable, Messagable {

    List<Character> search(String path);
}
