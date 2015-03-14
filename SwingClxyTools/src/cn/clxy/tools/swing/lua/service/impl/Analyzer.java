package cn.clxy.tools.swing.lua.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.clxy.tools.swing.lua.domain.Character;
import cn.clxy.tools.swing.lua.domain.Item;

class Analyzer {

    private List<String> lines;

    public Analyzer(List<String> lines) {
        this.lines = lines;
    }

    public List<Character> analyze() {

        List<Character> cs = new ArrayList<Character>();

        String server = null;
        Character character = null;
        for (String line : lines) {

            if (isServer(line)) {
                server = getServer(line);
                continue;
            }

            if (isCharacter(line)) {
                character = new Character(server, getCharacter(line));
                continue;
            }

            if (isItem(line)) {
                Item item = getItem(line);
                if (item != null) {
                    character.addItem(item);
                }
                continue;
            }

            if (isCharacterEnd(line)) {
                cs.add(character);
                character = null;
            }
        }

        return cs;
    }

    private boolean isServer(String line) {
        return pServer.matcher(line).matches();
    }

    private boolean isCharacter(String line) {
        return pCharacter.matcher(line).matches();
    }

    private boolean isCharacterEnd(String line) {
        return pCharacterEnd.matcher(line).matches();
    }

    private boolean isItem(String line) {
        return pItem.matcher(line).matches();
    }

    private String getServer(String line) {
        Matcher m = pServer.matcher(line);
        m.find();
        return m.group(1);
    }

    private String getCharacter(String line) {
        Matcher m = pCharacter.matcher(line);
        m.find();
        return m.group(1);
    }

    private Item getItem(String line) {

        Matcher m = pItem.matcher(line);
        m.find();

        String itemString = m.group(1);
        Item item = null;

        log.debug(itemString);
        for (ItemAnalyzer analyzer : analyzers) {
            if (analyzer.isMe(itemString)) {
                item = analyzer.analyze(itemString);
                break;
            }
        }
        log.debug(item);
        return item;
    }

    interface ItemAnalyzer {

        boolean isMe(String string);

        Item analyze(String string);
    }

    private ItemAnalyzer[] analyzers = {

    new ItemAnalyzer() {

        @Override
        public boolean isMe(String string) {
            return string.indexOf("=") < 0;
        }

        @Override
        public Item analyze(String string) {

            string = string.replaceAll("\"", "");
            if ("nil".equals(string)) {
                return null;
            }

            String[] ary = string.split("\\,");
            String id = getIdFromItemFormat(ary[0]);
            Integer count = ary.length > 1 ? Integer.valueOf(ary[1]) : 1;
            Item item = new Item(id);
            item.setCount(count);
            return item;
        }
    }, new ItemAnalyzer() {

        @Override
        public boolean isMe(String string) {
            return string.matches("^\\[(0|\\-100|\\-200|\"numBankSlots\")\\].*");
        }

        @Override
        public Item analyze(String string) {
            return null;
        }
    }, new ItemAnalyzer() {

        @Override
        public boolean isMe(String string) {
            return string.matches("^\\[\"g\"\\].*");
        }

        @Override
        public Item analyze(String string) {

            Matcher m = pSlot.matcher(string);
            m.find();
            String itemString = m.group(2);
            Item item = new Item("g", "Gold", Integer.valueOf(itemString));
            item.setTip(item.getName());
            return item;
        }
    }, new ItemAnalyzer() {

        @Override
        public boolean isMe(String string) {
            return pSlot.matcher(string).matches();
        }

        @Override
        public Item analyze(String string) {

            Matcher m = pSlot.matcher(string);
            m.find();
            String slotKey = m.group(1);
            String itemString = m.group(2);

            String[] ary = itemString.split("\\,");
            String id = null;
            Integer count = 1;
            if (slotKey.endsWith("00")) {
                id = getIdFromItemFormat(ary[1]);
            } else {
                id = getIdFromItemFormat(ary[0]);
                count = ary.length > 1 ? Integer.valueOf(ary[1]) : 1;
            }
            Item item = new Item(id);
            item.setCount(count);
            return item;
        }
    } };

    /**
     * Like 'item:32494:0:2835:2741:0:0:0:343113640'
     * @param string
     * @return
     */
    private static String getIdFromItemFormat(String string) {

        String key = "item:";
        if (string.indexOf(key) < 0) {
            return string;
        }

        String result = string.substring(key.length());
        result = result.substring(0, result.indexOf(':'));
        return result;
    }

    private static final Pattern pServer = Pattern.compile("^\t{1}\\[\"(.+)\".*\\{");
    private static final Pattern pCharacter = Pattern.compile("^\t{2}\\[\"(.+)\".*");
    private static final Pattern pCharacterEnd = Pattern.compile("^\t{2}\\}\\,");
    private static final Pattern pItem = Pattern.compile("^\t{3}(.+)\\,.*");
    private static final Pattern pSlot = Pattern.compile("\\[\"?(.+)\"?\\]\\s=\\s\"?([^\"]+)\"?");
    private static final Log log = LogFactory.getLog(Analyzer.class);
}
