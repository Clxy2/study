package cn.clxy.tools.webdriver.service.impl;

import java.io.Reader;
import java.io.StringReader;
import java.util.Enumeration;

import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.TreeNode;

public class Parser {

    protected Visitor visitor = new Visitor() {

        @Override
        public boolean visit(Element e) {

            TreeNode t = (TreeNode) e;
            Enumeration<Element> ts = t.children();
            if (ts == null) {
                return false;
            }
            while (ts.hasMoreElements()) {
                visit(ts.nextElement());
            }
            return false;
        }
    };

    public void parse(String content) {

        try {
            HTMLEditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            // TODO 文字化け可能かな～！
            doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
            Reader reader = new StringReader(content);
            kit.read(reader, doc, 0);

            // Get an iterator for all HTML tags.
            ElementIterator it = new ElementIterator(doc);
            Element elem;

            while ((elem = it.next()) != null) {
                boolean found = visitor.visit(elem);
                if (found) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static interface Visitor {
        boolean visit(Element e);
    }
}
