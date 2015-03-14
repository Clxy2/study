package cn.clxy.tools.swing.lua;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.beansbinding.AutoBinding;

import cn.clxy.tools.core.swing.ColumnInfo;
import cn.clxy.tools.core.swing.ImageRenderer;
import cn.clxy.tools.core.swing.TableBinding;
import cn.clxy.tools.core.swing.TextAreaRenderer;
import cn.clxy.tools.swing.lua.domain.Character;
import cn.clxy.tools.swing.lua.domain.Filter;
import cn.clxy.tools.swing.lua.domain.Item;
import cn.clxy.tools.swing.lua.domain.ItemRow;

/**
 * Memo table.
 * @author clxy
 */
public class ItemsTable extends JTable {

    private List<ColumnInfo> cis;
    private TableBinding binding;
    private List<ItemRow> rows = new ArrayList<ItemRow>();
    private List<ItemRow> allRows = new ArrayList<ItemRow>();

    public ItemsTable(List<Character> cs, List<ColumnInfo> cis) {

        super();
        this.cis = cis;

        TableColumnModel columns = getColumnModel();
        for (ColumnInfo ci : cis) {
            TableColumn column = new TableColumn();
            column.setHeaderValue(ci.getTitle());
            columns.addColumn(column);
        }

        setCharacters(cs);
    }

    public void doFilter(Filter filter) {

        if (filter == null) {
            rebind(allRows);
            return;
        }

        List<ItemRow> filtered = new ArrayList<ItemRow>();
        for (ItemRow row : allRows) {
            if (filter.doFilter(row)) {
                filtered.add(row);
            }
        }

        rebind(filtered);
    }

    public void setCharacters(List<Character> characters) {

        TableColumnModel columns = getColumnModel();
        // detail column use textarea.
        TableColumn column = columns.getColumn(4);
        column.setCellRenderer(new TextAreaRenderer());
        column = columns.getColumn(1);
        column.setCellRenderer(new ImageRenderer());

        allRows.clear();
        allRows.addAll(getRows(characters));

        rebind(allRows);
    }

    private void rebind(List<ItemRow> list) {

        if (binding != null) {
            binding.unbind();
        }

        rows.clear();
        rows.addAll(list);

        if (binding != null) {
            binding.bind();
        }
    }

    public TableBinding getBinding(AutoBinding.UpdateStrategy strategy) {

        binding = new TableBinding(strategy, rows, this);
        TableBinding.setColumnBinding(cis, binding);
        return binding;
    }

    public List<ItemRow> getRows() {
        return rows;
    }

    private static Collection<ItemRow> getRows(final List<Character> cs) {

        Map<String, ItemRow> map = new TreeMap<String, ItemRow>();

        for (Character c : cs) {
            for (Item item : c.getItems()) {

                String key = item.getId();
                ItemRow row = map.get(key);
                if (row == null) {
                    row = new ItemRow();
                    map.put(key, row);
                }
                row.addItem(c.getName(), item);
            }
        }
        return map.values();
    }

    /**
     * Default serialVersion.
     */
    private static final long serialVersionUID = 1L;
}
