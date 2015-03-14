package cn.clxy.tools.swing.lua.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.clxy.tools.core.utils.FileUtil;
import cn.clxy.tools.core.utils.StringUtil;
import cn.clxy.tools.swing.lua.domain.Character;
import cn.clxy.tools.swing.lua.service.ItemService;

public class ItemServiceImpl extends AbstractLuaService implements ItemService {

    @Override
    public List<Character> search(String path) {

        stop = false;
        log.debug("In, path=" + path);

        List<Character> result = new ArrayList<Character>();

        if (StringUtil.isBlank(path)) {
            return result;
        }

        File wowFolder = new File(path);
        if (!wowFolder.exists() || !wowFolder.isDirectory()) {
            return result;
        }

        List<File> files = FileUtil.listByName(wowFolder, target);
        messenger.say(files.size() + " file(s) found.");
        if (files.size() == 0) {
            return result;
        }

        if (stop)
            return result;

        for (File file : files) {

            messenger.say("Analyzing file " + file.getName());

            List<String> lines = FileUtil.readAllLines(file);
            List<Character> cs = new Analyzer(lines).analyze();
            result.addAll(cs);

            if (stop)
                break;
        }

        log.debug(result);
        return result;
    }

    private final static String target = "Bagnon_Forever.lua";
    private static final Log log = LogFactory.getLog(ItemServiceImpl.class);
}
