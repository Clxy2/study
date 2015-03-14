package cn.clxy.tools.swing.memo;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.beansbinding.AutoBinding;

import cn.clxy.tools.core.swing.ColumnInfo;
import cn.clxy.tools.core.swing.TableBinding;
import cn.clxy.tools.swing.memo.domain.Memo;

/**
 * Memo table.
 * @author clxy
 */
public class MemoTable extends JTable {

    private List<Memo> memos;
    private List<ColumnInfo> cis;
    private static Color background = new Color(230, 230, 204);

    public MemoTable(List<Memo> memos, List<ColumnInfo> cis) {

        super();
        this.memos = memos;
        this.cis = cis;
        // TODO Below code should be public.
        TableColumnModel columns = getColumnModel();
        for (ColumnInfo ci : cis) {
            TableColumn column = new TableColumn();
            column.setHeaderValue(ci.getTitle());
            columns.addColumn(column);
        }
    }

    public TableBinding getBinding(AutoBinding.UpdateStrategy strategy) {

        TableBinding binding = new TableBinding(strategy, memos, this);
        TableBinding.setColumnBinding(cis, binding);
        return binding;
    }

    /**
     * Set style for finished memo.
     * @param renderer
     * @param row
     * @param column
     * @return
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

        Component component = super.prepareRenderer(renderer, row, column);
        if (isRowSelected(row)) {
            return component;
        }

        Memo m = memos.get(row);
        if (m.getFinished() && component instanceof JComponent) {
            JComponent jc = ((JComponent) component);
            jc.setBackground(background);
            jc.setForeground(Color.DARK_GRAY);
        }
        return component;
    }
    /**
     * Default serialVersion.
     */
    private static final long serialVersionUID = 1L;
}
