package cn.clxy.tools.webdriver.service.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.tree.TreeNode;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cn.clxy.tools.webdriver.util.BeanUtil;

public class SkyFormParser extends Parser {

    public SkyFormParser(String name) {
        visitor = new SkyFormVisitor(name);
    }

    public String getAction() {
        return ((SkyFormVisitor) visitor).getAction();
    }

    public List<NameValuePair> getParams() {
        return ((SkyFormVisitor) visitor).getParams();
    }

    private static class SkyFormVisitor implements Visitor {

        private String name;
        private String action;
        private List<NameValuePair> params = new ArrayList<NameValuePair>();

        public SkyFormVisitor(String name) {
            this.name = name;
        }

        @Override
        public boolean visit(Element e) {

            if (!"form".equals(e.getName())) {
                return false;
            }

            AttributeSet as = e.getAttributes();
            if (!name.equals(as.getAttribute(HTML.Attribute.NAME))) {
                return false;
            }

            action = (String) as.getAttribute(HTML.Attribute.ACTION);
            handleElement(e);

            return true;
        }

        private void handleElement(Element e) {

            String nm = e.getName();
            if (BeanUtil.in(nm, new Object[] { "input", "textarea", "select" })) {
                AttributeSet a = e.getAttributes();
                params.add(new BasicNameValuePair((String) a.getAttribute(HTML.Attribute.NAME),
                        (String) a.getAttribute(HTML.Attribute.VALUE)));
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

        public String getAction() {
            return action;
        }

        public List<NameValuePair> getParams() {
            return params;
        }
    }
}
