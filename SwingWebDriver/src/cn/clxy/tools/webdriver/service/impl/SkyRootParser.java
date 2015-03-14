package cn.clxy.tools.webdriver.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.tree.TreeNode;

import cn.clxy.tools.webdriver.model.ClxyFile;

public class SkyRootParser extends Parser {

    public SkyRootParser() {
        visitor = new SkyRootVisitor();
    }

    public List<ClxyFile> getRoot() {
        return ((SkyRootVisitor) visitor).getRoot();
    }

    private static class SkyRootVisitor implements Visitor {

        private List<ClxyFile> root = new ArrayList<ClxyFile>();

        public List<ClxyFile> getRoot() {
            return root;
        }

        @Override
        public boolean visit(Element e) {

            if (!e.getName().equals("div")) {
                return false;
            }

            Object id = e.getAttributes().getAttribute(HTML.Attribute.ID);
            if (!"documentsTileView".equals(id)) {
                return false;
            }

            handleElement(e);
            return true;
        }

        private void handleElement(Element e) {

            System.out.println(e.getName() + " - "
                    + e.getAttributes().getAttribute(HTML.Attribute.CLASS));
            if (e.getName().equals("a")) {
                handleLink(e);
                return;
            }

            TreeNode tn = (TreeNode) e;
            Enumeration<Element> ts = tn.children();
            if (ts == null) {
                return;
            }

            while (ts.hasMoreElements()) {
                handleElement(ts.nextElement());
            }
        }

        private void handleLink(Element e) {

            AttributeSet as = e.getAttributes();
            Object clazz = as.getAttribute(HTML.Attribute.CLASS);
            if (!clazz.equals("tvLink")) {
                return;
            }

            ClxyFile f = new ClxyFile();
            // TODO 暫定的にTitleを設定する。
            f.setName((String) as.getAttribute(HTML.Attribute.TITLE));
            f.setPath(Arrays.asList(new String[] { "test" }));

            System.out.println(as);

            root.add(f);
        }
    }
}
