package cn.clxy.tools.swing.lua.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.DefaultPersistenceDelegate;
import java.beans.PersistenceDelegate;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import cn.clxy.tools.core.utils.BeanUtil;
import cn.clxy.tools.core.utils.HttpUtil;
import cn.clxy.tools.swing.lua.domain.Character;
import cn.clxy.tools.swing.lua.domain.Item;
import cn.clxy.tools.swing.lua.domain.ItemInfo;
import cn.clxy.tools.swing.lua.service.ItemInfoService;

public class ItemInfoServiceImpl extends AbstractLuaService implements ItemInfoService {

    private static HashMap<String, ItemInfo> caches;
    private HttpClient client = new DefaultHttpClient();

    public ItemInfoServiceImpl() {

        if (caches != null) {
            return;
        }
        caches = loadFromFile();
        log.debug(caches);
    }

    @Override
    public void setItemInfo(List<Character> characters) {

        stop = false;

        boolean changed = false;
        int tryNetCount = 0;

        try {
            for (Character c : characters) {

                for (Item item : c.getItems()) {

                    if (stop)
                        break;

                    if (item.getTip() != null) {
                        continue;
                    }

                    String key = item.getId();
                    ItemInfo ii = caches.get(key);
                    if (ii == null) {
                        tryNetCount++;
                        ii = loadFromNet(item);
                        caches.put(key, ii);
                        changed = true;
                        if ((tryNetCount % bufferCount) == 0)
                            save();
                    } else {
                        loadImage(ii);
                    }
                    item.setInfo(ii);
                }

                if (stop)
                    break;
            }
        } finally {

            log.debug("Load from net " + tryNetCount + " times.");
            // don't waste.
            if (changed) {
                save();
            }
        }
    }

    private void save() {

        BeanUtil.encodeXML(caches, new File(addImagePath(itemFile, false)), pds);
        messenger.say(caches.size() + " item saved.");
    }

    private ItemInfo loadFromNet(Item item) {

        ItemInfo result = new ItemInfo();

        String id = item.getId();
        result.setId(id);
        result.setName("unknown");
        result.setTip("unknown");

        String netInfo = null;
        try {
            netInfo = HttpUtil.simpleGet(client, String.format(url_item, id));
        } catch (Exception e) {
            log.debug(e);
            return result;
        }

        Matcher matcher = pJavascript.matcher(netInfo);
        if (!matcher.matches()) {
            return result;
        }

        String imageName = matcher.group(2);
        result.setImageName(imageName);
        result.setTip(matcher.group(3));

        String imageUrl = String.format(url_image, imageName);
        try {
            BufferedImage image = ImageIO.read(new URL(imageUrl));
            ImageIO.write(image, imageFormat, new File(addImagePath(imageName)));
            result.setImage(image);
        } catch (Exception e) {
            log.debug(e);
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private static HashMap<String, ItemInfo> loadFromFile() {

        HashMap<String, ItemInfo> result = new HashMap<String, ItemInfo>();

        new File(imageDir).mkdirs();

        File file = new File(addImagePath(itemFile, false));
        if (!file.exists()) {
            BeanUtil.encodeXML(result, file, pds);
            return result;
        }

        result = (HashMap<String, ItemInfo>) BeanUtil.decodeXML(file);
        return result;
    }

    private static void loadImage(ItemInfo ii) {

        String imageName = ii.getImageName();
        if (imageName == null) {
            return;
        }

        Image image = null;
        try {
            image = ImageIO.read(new File(addImagePath(imageName)));
        } catch (IOException e) {
            log.debug(e);
        }
        ii.setImage(image);
    }

    private static String addImagePath(String file) {

        return addImagePath(file, true);
    }

    private static String addImagePath(String file, boolean ext) {

        String result = imageDir + File.separator + file;
        if (ext) {
            result += "." + imageFormat;
        }
        return result;
    }

    private static final String imageFormat = "jpg";
    private static final String imageDir = "images";
    private static final Pattern pJavascript = Pattern
            .compile(".*\\{id:([0-9]+)\\,icon:\\'(.*)\\'\\,tip:\\'(.*)\\'\\}\\);");
    private static final int bufferCount = 50;
    private static final String itemFile = "item.cached.xml";
    private static final String url_image = "http://db1.178.com/wow/icons/m/%s." + imageFormat;
    // private static final String url_item = "http://www.atlas-o-line.com/tooltip/?item=%s";
    private static final String url_item = "http://db.178.com/wow/cn/a/item/%s.js";
    private static final Log log = LogFactory.getLog(ItemInfoServiceImpl.class);

    private static Map<Class<?>, PersistenceDelegate> pds = new HashMap<Class<?>, PersistenceDelegate>();
    static {
        pds.put(ItemInfo.class, new DefaultPersistenceDelegate(new String[] { "id", "name",
                "imageName", "tip" }));
    }
}
