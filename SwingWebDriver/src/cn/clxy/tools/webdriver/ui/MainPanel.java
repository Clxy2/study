package cn.clxy.tools.webdriver.ui;

import javax.swing.JFrame;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.application.Task.BlockingScope;

import cn.clxy.tools.webdriver.App;
import cn.clxy.tools.webdriver.model.Account;
import cn.clxy.tools.webdriver.service.WebDriverService;
import cn.clxy.tools.webdriver.service.impl.SkyService;

/**
 * @author clxy
 */
public class MainPanel extends javax.swing.JPanel {

    private Account account;
    // TODO injection.
    private WebDriverService service = new SkyService();

    /** Creates new form MainPanel */
    public MainPanel() {
        initComponents();
    }

    @Action(block = BlockingScope.APPLICATION)
    public Task selectAccount() {

        JFrame mainFrame = App.getApplication().getMainFrame();
        AccountDialog adg = new AccountDialog(mainFrame, true);
        adg.setLocationRelativeTo(mainFrame);
        App.getApplication().show(adg);
        account = adg.getAccount();
        adg.dispose();
        /*
         * if (account == null) { return null; }
         */// TODO do login
        return new Task(App.getApplication()) {

            @Override
            protected Object doInBackground() throws Exception {
                service.getRoot(account);
                return null;
            }
        };
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        folderPanel = new javax.swing.JPanel();
        folderScrollPane = new javax.swing.JScrollPane();
        folderTree = new javax.swing.JTree();
        folderLabel = new javax.swing.JLabel();
        loginPanel = new javax.swing.JPanel();
        loginButton = new javax.swing.JButton();
        fileScrollPane = new javax.swing.JScrollPane();
        detailTable = new javax.swing.JTable();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        splitPane.setDividerLocation(150);
        splitPane.setName("splitPane"); // NOI18N

        folderPanel.setName("folderPanel"); // NOI18N
        folderPanel.setLayout(new java.awt.BorderLayout());

        folderScrollPane.setName("folderScrollPane"); // NOI18N

        folderTree.setDragEnabled(true);
        folderTree.setName("folderTree"); // NOI18N
        folderScrollPane.setViewportView(folderTree);

        folderPanel.add(folderScrollPane, java.awt.BorderLayout.CENTER);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
                .getInstance(cn.clxy.tools.webdriver.App.class).getContext().getResourceMap(
                        MainPanel.class);
        folderLabel.setText(resourceMap.getString("folderLabel.text")); // NOI18N
        folderLabel.setName("folderLabel"); // NOI18N
        folderPanel.add(folderLabel, java.awt.BorderLayout.PAGE_START);

        loginPanel.setName("loginPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(
                cn.clxy.tools.webdriver.App.class).getContext().getActionMap(MainPanel.class, this);
        loginButton.setAction(actionMap.get("selectAccount")); // NOI18N
        loginButton.setText(resourceMap.getString("loginButton.text")); // NOI18N
        loginButton.setName("loginButton"); // NOI18N
        loginPanel.add(loginButton);

        folderPanel.add(loginPanel, java.awt.BorderLayout.PAGE_END);

        splitPane.setLeftComponent(folderPanel);

        fileScrollPane.setName("fileScrollPane"); // NOI18N

        detailTable.setDragEnabled(true);
        detailTable.setName("detailTable"); // NOI18N
        fileScrollPane.setViewportView(detailTable);

        splitPane.setRightComponent(fileScrollPane);

        add(splitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable detailTable;
    private javax.swing.JScrollPane fileScrollPane;
    private javax.swing.JLabel folderLabel;
    private javax.swing.JPanel folderPanel;
    private javax.swing.JScrollPane folderScrollPane;
    private javax.swing.JTree folderTree;
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JSplitPane splitPane;
    // End of variables declaration//GEN-END:variables

    private static final long serialVersionUID = 1L;
}