/*
 * View.java
 */
package cn.clxy.game.tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import org.jdesktop.application.Action;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.TaskMonitor;

import cn.clxy.game.tetris.controller.Game;
import cn.clxy.game.tetris.controller.MessagePrinter;
import cn.clxy.game.tetris.controller.PlayerGame;
import cn.clxy.game.tetris.controller.RobotGame;
import cn.clxy.game.tetris.util.ClassUtil;
import cn.clxy.game.tetris.view.ScoreHistoryDialog;
import cn.clxy.game.tetris.view.SinglePanel;
import cn.clxy.game.tetris.view.ViewUtil;

/**
 * The application's main frame.
 */
public class View extends FrameView {

    public View(SingleFrameApplication app) {

        super(app);

        initComponents();
        initMenuGroup();
        ViewUtil.setTheme();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void startPlayer() {
        controlGame(playerMenuItem, PlayerGame.class);
    }

    @Action
    public void startRobot() {
        controlGame(robotMenuItem, RobotGame.class);
    }

    @Action
    public void showHistoryBox() {

        JFrame mainFrame = App.getApplication().getMainFrame();
        historyBox = new ScoreHistoryDialog(mainFrame);
        historyBox.setLocationRelativeTo(mainFrame);
        App.getApplication().show(historyBox);
    }

    @Action
    public void showAboutBox() {

        if (aboutBox == null) {
            JFrame mainFrame = App.getApplication().getMainFrame();
            aboutBox = new AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        App.getApplication().show(aboutBox);
    }

    private void controlGame(JMenuItem mi, Class clazz) {

        String text = mi.getText();
        if (text.startsWith("End")) {

            game.over();
            enableGroup(true);
            mi.setText(text.replace("End", "Start"));
            return;
        }

        if (text.startsWith("Start")) {

            game = (Game) ClassUtil.getInstance(clazz);
            game.setMainPanel((SinglePanel) mainPanel);
            game.setMessagePrinter(mp);
            game.start();
            enableGroup(false);
            mi.setEnabled(true);
            mi.setText(text.replace("Start", "End"));
            return;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        playerMenuItem = new javax.swing.JMenuItem();
        robotMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        vsAIMenuItem = new javax.swing.JMenuItem();
        vsNetMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu scoreMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem historyMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        mainPanel = new SinglePanel();

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
                .getInstance(cn.clxy.game.tetris.App.class).getContext().getResourceMap(View.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(
                cn.clxy.game.tetris.App.class).getContext().getActionMap(View.class, this);
        playerMenuItem.setAction(actionMap.get("startPlayer")); // NOI18N
        playerMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_F2, 0));
        playerMenuItem.setText(resourceMap.getString("playerMenuItem.text")); // NOI18N
        playerMenuItem.setName("playerMenuItem"); // NOI18N
        fileMenu.add(playerMenuItem);

        robotMenuItem.setAction(actionMap.get("startRobot")); // NOI18N
        robotMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_F3, 0));
        robotMenuItem.setText(resourceMap.getString("robotMenuItem.text")); // NOI18N
        robotMenuItem.setName("robotMenuItem"); // NOI18N
        fileMenu.add(robotMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        vsAIMenuItem.setAction(actionMap.get("start")); // NOI18N
        vsAIMenuItem.setText(resourceMap.getString("vsAIMenuItem.text")); // NOI18N
        vsAIMenuItem.setName("vsAIMenuItem"); // NOI18N
        fileMenu.add(vsAIMenuItem);

        vsNetMenuItem.setAction(actionMap.get("start")); // NOI18N
        vsNetMenuItem.setText(resourceMap.getString("vsNetMenuItem.text")); // NOI18N
        vsNetMenuItem.setName("vsNetMenuItem"); // NOI18N
        fileMenu.add(vsNetMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        scoreMenu.setText(resourceMap.getString("scoreMenu.text")); // NOI18N
        scoreMenu.setName("scoreMenu"); // NOI18N

        historyMenuItem.setAction(actionMap.get("showHistoryBox")); // NOI18N
        historyMenuItem.setText(resourceMap.getString("historyMenuItem.text")); // NOI18N
        historyMenuItem.setName("historyMenuItem"); // NOI18N
        scoreMenu.add(historyMenuItem);

        menuBar.add(scoreMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(statusPanelLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addComponent(statusPanelSeparator,
                javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE).addGroup(
                statusPanelLayout.createSequentialGroup().addContainerGap().addComponent(
                        statusMessageLabel).addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                                javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                                statusAnimationLabel).addContainerGap()));
        statusPanelLayout.setVerticalGroup(statusPanelLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                statusPanelLayout.createSequentialGroup().addComponent(statusPanelSeparator,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 2,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(
                        statusPanelLayout.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                                statusMessageLabel).addComponent(statusAnimationLabel)
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(3, 3, 3)));

        mainPanel.setName("mainPanel"); // NOI18N

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem playerMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem robotMenuItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenuItem vsAIMenuItem;
    private javax.swing.JMenuItem vsNetMenuItem;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private JDialog historyBox;
    private Game game;
    private List<JMenuItem> mis;
    private MessagePrinter mp = new MessagePrinter() {

        @Override
        public void print(String text) {
            statusMessageLabel.setText((text == null) ? "" : text);
            messageTimer.restart();
        }
    };

    private void initMenuGroup() {

        mis = new ArrayList<JMenuItem>();
        mis.add(playerMenuItem);
        mis.add(robotMenuItem);
        mis.add(vsAIMenuItem);
        mis.add(vsNetMenuItem);
    }

    private void enableGroup(boolean enable) {

        for (JMenuItem mi : mis) {
            mi.setEnabled(enable);
        }
    }
}
