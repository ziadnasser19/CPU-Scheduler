package GUI;

import Models.Process;
import Models.ProcessManager;
import Algorithms.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class InputPanel extends JPanel {
    private final ProcessManager manager;
    private final ReadyQueuePanel readyPanel;
    private final ResultPanel resultPanel;
    private final GanttChartPanel ganttPanel;
    private final AveragePanel averagePanel;

    private final JLabel pidLabel, burstLabel, priorityLabel, arrivalLabel, algoLabel;
    private final JTextField burstField, priorityField, arrivalField;
    private final JComboBox<String> algorithmBox;
    private final JButton addButton, startButton, clearAllButton;

    public InputPanel(ProcessManager manager,
                      ReadyQueuePanel readyPanel,
                      ResultPanel resultPanel,
                      GanttChartPanel ganttPanel,
                      AveragePanel averagePanel) {
        this.manager      = manager;
        this.readyPanel   = readyPanel;
        this.resultPanel  = resultPanel;
        this.ganttPanel   = ganttPanel;
        this.averagePanel = averagePanel;

        pidLabel      = new JLabel("Process #" + manager.nextPID());
        burstLabel    = new JLabel("Burst Time:");
        priorityLabel = new JLabel("Priority:");
        arrivalLabel  = new JLabel("Arrival Time:");
        algoLabel     = new JLabel("Algorithm:");

        burstField    = new JTextField(6);
        priorityField = new JTextField("0", 4);
        arrivalField  = new JTextField("0", 4);

        algorithmBox = new JComboBox<>(new String[]{
                "FCFS", "SJF Non-Preemptive", "SJF Preemptive",
                "Priority Non-Preemptive", "Priority Preemptive", "Round Robin"
        });

        addButton      = new JButton("Add Process");
        startButton    = new JButton("Start Scheduling");
        clearAllButton = new JButton("Clear All");

        setBorder(BorderFactory.createTitledBorder("Input & Controls"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3,6,3,6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=6; add(pidLabel, gbc);
        gbc.gridy=1; gbc.gridwidth=1;
        gbc.gridx=0; add(burstLabel,    gbc);
        gbc.gridx=1; add(burstField,    gbc);
        gbc.gridx=2; add(priorityLabel, gbc);
        gbc.gridx=3; add(priorityField, gbc);
        gbc.gridx=4; add(arrivalLabel,  gbc);
        gbc.gridx=5; add(arrivalField,  gbc);
        gbc.gridy=2;
        gbc.gridx=0; add(algoLabel,     gbc);
        gbc.gridx=1; add(algorithmBox,  gbc);
        gbc.gridy=3;
        gbc.gridx=0; add(addButton,     gbc);
        gbc.gridx=1; add(startButton,   gbc);
        gbc.gridx=2; add(clearAllButton,gbc);

        // === تعديل: الحقول دايمًا مرئية لكن معطّلة مبدئيًا ===
        priorityLabel.setEnabled(false);
        priorityField.setEnabled(false);
        arrivalLabel.setEnabled(false);
        arrivalField.setEnabled(false);

        algorithmBox.addActionListener(e -> {
            updateArrivalEnabled();
            updatePriorityEnabled();
            if (!arrivalField.isEnabled()) arrivalField.setText("0");
            if (!priorityField.isEnabled()) priorityField.setText("0");
        });
        addButton.addActionListener(e -> onAdd());
        startButton.addActionListener(e -> onStart());
        clearAllButton.addActionListener(e -> onClearAll());
    }

    // === تعديل: تفعيل/تعطيل بدل إظهار/إخفاء ===
    private void updateArrivalEnabled() {
        String algo = (String)algorithmBox.getSelectedItem();
        boolean enable = algo.equals("SJF Preemptive") || algo.equals("Priority Preemptive");
        arrivalLabel.setEnabled(enable);
        arrivalField.setEnabled(enable);
    }

    private void updatePriorityEnabled() {
        String algo = (String)algorithmBox.getSelectedItem();
        boolean enable = algo.contains("Priority");
        priorityLabel.setEnabled(enable);
        priorityField.setEnabled(enable);
    }

    // باقي الكود من onAdd(), onStart(), onClearAll() يبقى كما هو دون تغيير...
    private void onAdd() {
        try {
            int burst   = Integer.parseInt(burstField.getText().trim());
            int prio    = priorityField.isEnabled()
                    ? Integer.parseInt(priorityField.getText().trim())
                    : 0;
            int arrival = arrivalField.isEnabled()
                    ? Integer.parseInt(arrivalField.getText().trim())
                    : 0;

            if (burst <= 0) {
                throw new NumberFormatException("Burst time must be greater than 0.");
            }
            if(prio < 0 || arrival < 0) {
                throw new NumberFormatException("Negative values are not allowed.");
            }

            manager.createProcess(burst, prio, arrival);
            readyPanel.clear();
            manager.getReadyQueue().forEach(readyPanel::addRow);
            pidLabel.setText("Process #" + manager.nextPID());
            burstField.setText("");
            priorityField.setText("");
            arrivalField.setText("0");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Enter valid integers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onStart() {
        manager.resetFinished();
        resultPanel.clear();
        ganttPanel.clear();
        averagePanel.clearAverages();

        SchedulingAlgorithm scheduler;
        String algo = (String)algorithmBox.getSelectedItem();
        switch (algo) {
            case "Round Robin":
                String input = JOptionPane.showInputDialog(
                        this,
                        "Enter Time Quantum:",
                        "Round Robin Quantum",
                        JOptionPane.QUESTION_MESSAGE
                );
                if (input == null) return;
                int q = Integer.parseInt(input.trim());
                scheduler = new RoundRobin(q, manager);
                break;
            case "FCFS":                      scheduler = new FCFS(manager); break;
            case "SJF Non-Preemptive":       scheduler = new SJFNonPreemptive(manager); break;
            case "SJF Preemptive":           scheduler = new SJFPreemptive(manager); break;
            case "Priority Non-Preemptive":  scheduler = new PriorityNonPreemptive(manager); break;
            case "Priority Preemptive":      scheduler = new PriorityPreemptive(manager); break;
            default:                         scheduler = new FCFS(manager);
        }

        scheduler.schedule();
        manager.getFinishedQueue().forEach(resultPanel::addRow);

        List<GanttChartPanel.ExecutionSegment> segments = manager.getFinishedQueue().stream()
                .map(p -> new GanttChartPanel.ExecutionSegment(
                        p.getProcessNumber(),
                        p.getStartTime(),
                        p.getEndTime()))
                .collect(Collectors.toList());

        if (scheduler instanceof RoundRobin) {
            segments = ((RoundRobin)scheduler).timeline.stream()
                    .map(s -> new GanttChartPanel.ExecutionSegment(s.pid, s.start, s.end))
                    .collect(Collectors.toList());
        } else if (scheduler instanceof PriorityPreemptive) {
            segments = ((PriorityPreemptive)scheduler).timeline.stream()
                    .map(s -> new GanttChartPanel.ExecutionSegment(s.pid, s.start, s.end))
                    .collect(Collectors.toList());
        } else if (scheduler instanceof SJFPreemptive) {
            segments = ((SJFPreemptive)scheduler).timeline.stream()
                    .map(s -> new GanttChartPanel.ExecutionSegment(s.pid, s.start, s.end))
                    .collect(Collectors.toList());
        }

        ganttPanel.setTimeline(segments);
        ganttPanel.repaint();

        double avgWait = manager.getFinishedQueue().stream()
                .mapToInt(Process::getWaitingTime).average().orElse(0);
        double avgTurn = manager.getFinishedQueue().stream()
                .mapToInt(Process::getTurnAroundTime).average().orElse(0);
        double avgResp = manager.getFinishedQueue().stream()
                .mapToInt(Process::getResponseTime).average().orElse(0);
        averagePanel.updateAverages(avgWait, avgTurn, avgResp);
    }

    private void onClearAll() {
        manager.resetAll();
        readyPanel.clear();
        resultPanel.clear();
        ganttPanel.clear();
        averagePanel.clearAverages();
        pidLabel.setText("Process #" + manager.nextPID());
        arrivalField.setText("0");
        priorityField.setText("0");
    }
}
