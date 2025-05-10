package GUI;

import Algorithms.RoundRobin;
import Models.Process;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GanttChartPanel extends JPanel {
    private List<RoundRobin.ExecutionSegment> segments;

    public GanttChartPanel() {
        setBorder(BorderFactory.createTitledBorder("Gantt Chart"));
        setPreferredSize(new Dimension(1100, 220));
    }

    /** استخدم لـ RR و non-RR على حد سواء */
    public void setTimeline(List<RoundRobin.ExecutionSegment> segs) {
        this.segments = new ArrayList<>(segs);
        repaint();
    }

    /** إذا أردنا توليد الشارت من finishedQueue */
    public void setTimelineFromFinished(Queue<Process> finished) {
        List<RoundRobin.ExecutionSegment> segs = new ArrayList<>();
        for (Process p : finished) {
            segs.add(new RoundRobin.ExecutionSegment(
                    p.getProcessNumber(), p.getStartTime(), p.getEndTime()));
        }
        this.segments = segs;
        repaint();
    }

    public void clear() {
        if (segments != null) segments.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (segments == null || segments.isEmpty()) return;

        g.setFont(g.getFont().deriveFont(12f));
        int y = 30;

        // نحسب العرض النسبي لكل وحدة زمنية
        int totalTime = segments.get(segments.size()-1).end;
        if (totalTime <= 0) return;
        double unitWidth = getWidth() / (double) totalTime;

        // نرسم كل قطعة
        for (RoundRobin.ExecutionSegment seg : segments) {
            int x = (int)(seg.start * unitWidth);
            int w = (int)((seg.end - seg.start) * unitWidth);
            g.setColor(Color.CYAN);
            g.fillRect(x, y, w, 40);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, w, 40);
            g.drawString("P" + seg.pid, x + w/2 - 5, y + 25);
            g.drawString(String.valueOf(seg.start), x, y + 60);
        }

        // نرسم النهاية النهائية
        RoundRobin.ExecutionSegment last = segments.get(segments.size()-1);
        int endX = (int)(last.end * unitWidth);
        endX = Math.min(endX, getWidth() - 20);
        g.drawString(String.valueOf(last.end), endX, y + 60);
    }
}
