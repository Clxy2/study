package cn.clxy.tools.swing.lua.service.impl;

import cn.clxy.tools.core.Messagable;
import cn.clxy.tools.core.Messenger;
import cn.clxy.tools.core.Stoppable;

public abstract class AbstractLuaService implements Stoppable, Messagable {

    protected boolean stop = false;
    protected Messenger messenger = Messenger.Default;

    @Override
    public void stop() {
        stop = true;
    }

    @Override
    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }
}
