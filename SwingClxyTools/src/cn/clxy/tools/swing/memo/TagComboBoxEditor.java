package cn.clxy.tools.swing.memo;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;

import cn.clxy.tools.core.utils.BeanUtil;
import cn.clxy.tools.swing.memo.domain.Tag;

/**
 * Editor fro tag combobox
 * @author clxy
 */
public class TagComboBoxEditor implements ComboBoxEditor, Serializable {

    private Tag tag;
    private JTextField text;

    public TagComboBoxEditor() {
        text = new JTextField();
        text.setBorder(BorderFactory.createEmptyBorder());
    }

    public Component getEditorComponent() {
        return text;
    }

    public void setItem(Object anObject) {

        if (anObject instanceof Tag) {
            tag = (Tag) anObject;
            text.setText(tag.getName());
            return;
        }
        if (anObject == null || anObject instanceof String) {
            String name = (String) anObject;
            text.setText(name);
            return;
        }
        throw new IllegalArgumentException();
    }

    public Object getItem() {

        String name = text.getText();
        if (tag != null && BeanUtil.equals(name, tag.getName())) {
            return tag;
        }

        tag = new Tag();
        tag.setName(name);
        return tag;
    }

    public void selectAll() {
        text.selectAll();
    }

    public void addActionListener(ActionListener l) {
        text.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        text.removeActionListener(l);
    }
    /**
     * Default serialVersion.
     */
    private static final long serialVersionUID = 1L;
}
