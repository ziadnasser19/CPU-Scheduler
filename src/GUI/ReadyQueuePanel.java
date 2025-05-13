package GUI;

import Models.Process;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReadyQueuePanel extends JPanel {
    private final DefaultTableModel model =
            new DefaultTableModel(new Object[]{"PID", "Burst", "Priority", "Arrival"}, 0);

    public ReadyQueuePanel() {
        setBorder(BorderFactory.createTitledBorder("Ready Queue"));
        setLayout(new BorderLayout());
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void addRow(Process p) {
        model.addRow(new Object[]{
                p.getProcessNumber(),
                p.getBurstTime(),
                p.getPriority(),
                p.getArrivalTime()
        });
    }

    public void clear() {
        model.setRowCount(0);
    }
}
