package cn.clxy.tools.swing.memo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;

import cn.clxy.tools.core.swing.ColumnInfo;
import cn.clxy.tools.core.swing.FormattedTextFieldRenderer;
import cn.clxy.tools.core.swing.SwingUtil;
import cn.clxy.tools.core.swing.TableBinding;
import cn.clxy.tools.core.swing.TextAreaRenderer;
import cn.clxy.tools.core.utils.BeanUtil;
import cn.clxy.tools.swing.App;
import cn.clxy.tools.swing.memo.domain.Memo;
import cn.clxy.tools.swing.memo.domain.Tag;
import cn.clxy.tools.swing.memo.service.MemoService;
import cn.clxy.tools.swing.memo.service.TagService;

/**
 * Tag edit panel.
 * @author clxy
 */
public class TagEditPanel extends javax.swing.JPanel {

    /**
     * All tags.
     */
    private List<Tag> tags = new ArrayList<Tag>();
    /**
     * Memos with selected newTag.
     */
    private List<Memo> memos = new ArrayList<Memo>();
    /**
     * memos <-> memos table.
     */
    private TableBinding memoBinding;
    /**
     * tags <-> tags table.
     */
    private TableBinding tagBinding;
    private MemoService memoService;
    private TagService tagService;

    /** Creates new form TagEditPanel */
    public TagEditPanel(MemoService memoService, TagService tagService) {

        super();
        this.memoService = memoService;
        this.tagService = tagService;
        initComponents();
        initBinding();
        SwingUtil.executeTask(searchTags());
    }

    @Action(block = Task.BlockingScope.COMPONENT)
    public Task searchTags() {

        return new Task(app) {

            @Override
            protected Object doInBackground() throws Exception {

                List<Tag> newTags = tagService.searchAll();

                memoBinding.unbind();
                memos.clear();

                tagsTable.getSelectionModel().removeListSelectionListener(rowListener);
                tagBinding.unbind();
                tags.clear();
                tags.addAll(newTags);
                tagBinding.bind();
                tagsTable.getSelectionModel().addListSelectionListener(rowListener);

                memoBinding.bind();
                return null;
            }
        };
    }

    @Action(block = Task.BlockingScope.COMPONENT)
    public void addTag() {

        Tag newTag = new Tag();
        newTag.setName("");

        if (!doSave(newTag)) {
            return;
        }
        tagBinding.unbind();
        tags.add(newTag);
        tagBinding.bind();

        int row = tags.indexOf(newTag);
        tagsTable.setRowSelectionInterval(row, row);

        selectTag();
    }

    @Action(block = Task.BlockingScope.COMPONENT)
    public void deleteTag() {

        Tag deleteTag = getSelectedTag();
        if (deleteTag == null) {
            return;
        }

        if (JOptionPane.showConfirmDialog(this, "Are you sure delete " + deleteTag.getName()
                + " ?\r\nClick OK to delete.", "Tag Delete", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.CANCEL_OPTION) {
            return;
        }

        tagService.delete(deleteTag);
        tagBinding.unbind();
        tags.remove(deleteTag);
        tagBinding.bind();
    }

    @Action
    public void selectTag() {

        memoBinding.unbind();
        memos.clear();

        Tag tag = getSelectedTag();
        saveButton.setEnabled(tag != null);
        if (tag == null) {
            memoBinding.bind();
            return;
        }

        String name = tag.getName();
        oldNameLabel.setText(name);
        nameTextField.setText(name);

        Map<String, Object> cond = new HashMap<String, Object>();
        cond.put("finished", true);
        cond.put("tag", tag);

        List<Memo> newMemos = memoService.search(cond);
        memos.addAll(newMemos);
        memoBinding.bind();
    }

    @Action(block = Task.BlockingScope.WINDOW, enabledProperty = "tagSelected")
    public void save() {

        Tag tag = getSelectedTag();
        if (tag == null) {
            return;
        }

        String name = nameTextField.getText();
        if (BeanUtil.equals(name, oldNameLabel.getText())) {
            return;
        }

        tag.setName(name);
        if (!doSave(tag)) {
            return;
        }
        SwingUtil.executeTask(searchTags());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The contentColumn of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mailSplitPane = new javax.swing.JSplitPane();
        tagsPanel = new javax.swing.JPanel();
        toolbarPanel = new javax.swing.JPanel();
        refreshButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        delButton = new javax.swing.JButton();
        tagsPane = new javax.swing.JScrollPane();
        tagsTable = new javax.swing.JTable();
        editPanel = new javax.swing.JPanel();
        memosScrollPane = new javax.swing.JScrollPane();
        memosTable = new MemoTable(memos, memoColumns);
        tagEditPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        oldNameLabel = new javax.swing.JLabel();
        toLabel = new javax.swing.JLabel();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        mailSplitPane.setName("mailSplitPane"); // NOI18N

        tagsPanel.setName("tagsPanel"); // NOI18N
        tagsPanel.setLayout(new java.awt.BorderLayout());

        toolbarPanel.setName("toolbarPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application
                .getInstance(cn.clxy.tools.swing.App.class).getContext()
                .getActionMap(TagEditPanel.class, this);
        refreshButton.setAction(actionMap.get("searchTags")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
                .getInstance(cn.clxy.tools.swing.App.class).getContext()
                .getResourceMap(TagEditPanel.class);
        refreshButton.setText(resourceMap.getString("refreshButton.text")); // NOI18N
        refreshButton.setName("refreshButton"); // NOI18N
        toolbarPanel.add(refreshButton);

        addButton.setAction(actionMap.get("addTag")); // NOI18N
        addButton.setText(resourceMap.getString("addButton.text")); // NOI18N
        addButton.setName("addButton"); // NOI18N
        toolbarPanel.add(addButton);

        delButton.setAction(actionMap.get("deleteTag")); // NOI18N
        delButton.setText(resourceMap.getString("delButton.text")); // NOI18N
        delButton.setName("delButton"); // NOI18N
        toolbarPanel.add(delButton);

        tagsPanel.add(toolbarPanel, java.awt.BorderLayout.PAGE_END);

        tagsPane.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap
                .getString("tagsPane.border.title"))); // NOI18N
        tagsPane.setName("tagsPane"); // NOI18N

        tagsTable.setAutoCreateRowSorter(true);
        tagsTable.setGridColor(resourceMap.getColor("tagsTable.gridColor")); // NOI18N
        tagsTable.setName("tagsTable"); // NOI18N
        SwingUtil.setTableHeaderAlignment(tagsTable, JLabel.CENTER);
        tagsTable.getTableHeader().setReorderingAllowed(false);
        tagsTable.setRowHeight(25);
        tagsTable.setRowMargin(3);
        tagsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tagsTable.getSelectionModel().addListSelectionListener(rowListener);
        tagsPane.setViewportView(tagsTable);

        tagsPanel.add(tagsPane, java.awt.BorderLayout.CENTER);

        mailSplitPane.setLeftComponent(tagsPanel);

        editPanel.setName("editPanel"); // NOI18N
        editPanel.setLayout(new java.awt.BorderLayout(5, 5));

        memosScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap
                .getString("memosScrollPane.border.title"))); // NOI18N
        memosScrollPane.setName("memosScrollPane"); // NOI18N

        memosTable.setAutoCreateRowSorter(true);
        memosTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        memosTable.setGridColor(resourceMap.getColor("memosTable.gridColor")); // NOI18N
        memosTable.setName("memosTable"); // NOI18N
        SwingUtil.setTableHeaderAlignment(memosTable, JLabel.CENTER);
        memosTable.getTableHeader().setReorderingAllowed(false);
        memosTable.setRowHeight(25);
        memosScrollPane.setViewportView(memosTable);

        editPanel.add(memosScrollPane, java.awt.BorderLayout.CENTER);

        tagEditPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap
                .getString("tagEditPanel.border.title"))); // NOI18N
        tagEditPanel.setName("tagEditPanel"); // NOI18N
        tagEditPanel.setLayout(new java.awt.GridBagLayout());

        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        tagEditPanel.add(nameLabel, gridBagConstraints);

        nameTextField.setText(resourceMap.getString("nameTextField.text")); // NOI18N
        nameTextField.setName("nameTextField"); // NOI18N
        nameTextField.setPreferredSize(new java.awt.Dimension(100, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        tagEditPanel.add(nameTextField, gridBagConstraints);

        saveButton.setAction(actionMap.get("save")); // NOI18N
        saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        tagEditPanel.add(saveButton, gridBagConstraints);

        oldNameLabel.setText(resourceMap.getString("oldNameLabel.text")); // NOI18N
        oldNameLabel.setBorder(new javax.swing.border.LineBorder(resourceMap
                .getColor("oldNameLabel.border.lineColor"), 1, true)); // NOI18N
        oldNameLabel.setName("oldNameLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        tagEditPanel.add(oldNameLabel, gridBagConstraints);

        toLabel.setText(resourceMap.getString("toLabel.text")); // NOI18N
        toLabel.setName("toLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        tagEditPanel.add(toLabel, gridBagConstraints);

        editPanel.add(tagEditPanel, java.awt.BorderLayout.PAGE_START);

        mailSplitPane.setRightComponent(editPanel);

        add(mailSplitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
     // Variables declaration - do not modify//GEN-BEGIN:variables

    private javax.swing.JButton addButton;
    private javax.swing.JButton delButton;
    private javax.swing.JPanel editPanel;
    private javax.swing.JSplitPane mailSplitPane;
    private javax.swing.JScrollPane memosScrollPane;
    private javax.swing.JTable memosTable;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel oldNameLabel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JPanel tagEditPanel;
    private javax.swing.JScrollPane tagsPane;
    private javax.swing.JPanel tagsPanel;
    private javax.swing.JTable tagsTable;
    private javax.swing.JLabel toLabel;
    private javax.swing.JPanel toolbarPanel;
    // End of variables declaration//GEN-END:variables
    /**
     * Default serialVersion.
     */
    private static final long serialVersionUID = 1L;
    private static final Application app = Application.getInstance(App.class);
    private static final ResourceMap resource = SwingUtil
            .getResource(App.class, TagEditPanel.class);
    private static final List<ColumnInfo> memoColumns = ColumnInfo.getColumns(resource, "memo");
    private static final List<ColumnInfo> tagColumns = ColumnInfo.getColumns(resource, "tag");
    private ListSelectionListener rowListener = new ListSelectionListener() {

        public void valueChanged(ListSelectionEvent e) {
            selectTag();
        }
    };

    private void initBinding() {

        BindingGroup group = new BindingGroup();
        memoBinding = ((MemoTable) memosTable).getBinding(AutoBinding.UpdateStrategy.READ);
        group.addBinding(memoBinding.getBinding());

        tagBinding = new TableBinding(AutoBinding.UpdateStrategy.READ_WRITE, tags, tagsTable);
        TableBinding.setColumnBinding(tagColumns, tagBinding);
        group.addBinding(tagBinding.getBinding());

        group.bind();

        TableColumnModel columns = memosTable.getColumnModel();
        // Tag
        TableColumn tagColumn = columns.getColumn(1);
        tagColumn.setCellRenderer(new FormattedTextFieldRenderer());
        // Content
        TableColumn contentColumn = columns.getColumn(2);
        contentColumn.setCellRenderer(new TextAreaRenderer());

        // Create date and update date.
        String dateFormat = resource.getString("date.format");
        TableColumn create = columns.getColumn(3);
        create.setCellRenderer(new FormattedTextFieldRenderer(new SimpleDateFormat(dateFormat)));
        TableColumn update = columns.getColumn(4);
        update.setCellRenderer(new FormattedTextFieldRenderer(new SimpleDateFormat(dateFormat)));
    }

    private Tag getSelectedTag() {

        int row = tagsTable.getSelectedRow();
        if (row < 0) {
            return null;
        }

        row = tagsTable.convertRowIndexToModel(row);
        return tags.get(row);
    }

    private boolean doSave(Tag tag) {

        try {
            tagService.edit(tag);
        } catch (Exception le) {
            JOptionPane.showConfirmDialog(this, "Edit tag failed!", "Edit Tag",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * For action save.<br>
     * @see #save()
     * @return
     */
    public boolean isTagSelected() {
        return getSelectedTag() != null;
    }
}
