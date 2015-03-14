package cn.clxy.tools.swing.memo;

import org.jdesktop.application.ResourceMap;

import cn.clxy.tools.core.swing.SwingUtil;
import cn.clxy.tools.swing.App;
import cn.clxy.tools.swing.memo.service.MemoService;
import cn.clxy.tools.swing.memo.service.TagService;

import com.google.inject.Inject;

/**
 * Memo main panel, contains Memo search panel and tag edit panel.
 * @author clxy
 */
public class MemoPanel extends javax.swing.JPanel {

    private static ResourceMap resource = SwingUtil.getResource(App.class, MemoPanel.class);

    /** Creates new form MemoPanel */
    @Inject
    public MemoPanel(MemoService memoService, TagService tagService) {

        super();
        initComponents();

        tabbedPane.addTab(
                resource.getString("memoListPanel.title"),
                new MemoListPanel(memoService, tagService));

        tabbedPane.addTab(
                resource.getString("tagEditPanel.title"),
                new TagEditPanel(memoService, tagService));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cn.clxy.tools.swing.App.class).getContext().getResourceMap(MemoPanel.class);
        tabbedPane.setForeground(resourceMap.getColor("tabbedPane.foreground")); // NOI18N
        tabbedPane.setFont(resourceMap.getFont("tabbedPane.font")); // NOI18N
        tabbedPane.setName("tabbedPane"); // NOI18N
        add(tabbedPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
    /**
     * Default serialVersion.
     */
    private static final long serialVersionUID = 1L;
}
