package cn.clxy.game.tetris.util;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.Task;
import org.jdesktop.application.TaskMonitor;
import org.jdesktop.application.TaskService;

/**
 * Utils for operating application.
 * @author clxy
 */
public class SwingUtil {

    /**
     * @param table
     * @param hAlignment
     */
    public static void setTableHeaderAlignment(JTable table, int hAlignment) {

        JTableHeader header = table.getTableHeader();
        TableCellRenderer headerRenderer = header.getDefaultRenderer();
        if (headerRenderer instanceof JLabel) {
            ((JLabel) headerRenderer).setHorizontalAlignment(hAlignment);
        }
    }

    /**
     * For making code shorter.
     * @param root
     * @param clazz
     * @return
     */
    public static ResourceMap getResource(Class root, Class clazz) {
        return Application.getInstance(root).getContext().getResourceMap(clazz);
    }

    /**
     * Execute a task handle.
     * @param task
     */
    public static void executeTask(Task task) {

        ApplicationContext ac = task.getApplication().getContext();

        TaskMonitor tm = ac.getTaskMonitor();
        TaskService ts = ac.getTaskService();
        tm.setForegroundTask(task);

        ts.execute(task);
    }

    /**
     * Get specified parent.
     * @param <T>
     * @param component
     * @param clazz
     * @return
     */
    public static <T> T getParent(Component component, Class<T> clazz) {

        if (component == null) {
            return null;
        }

        if (clazz.isInstance(component)) {
            return (T) component;
        }

        return getParent(component.getParent(), clazz);
    }

    /**
     * Close the parent window.
     * @see Window#dispose()
     * @param component
     */
    public static void disposeWindow(Component component) {

        if (component == null) {
            return;
        }

        Window window = getParent(component, Window.class);
        if (window == null) {
            return;
        }

        window.dispose();
    }

    private SwingUtil() {
    }
}
