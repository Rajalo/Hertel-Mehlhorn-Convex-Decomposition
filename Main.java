import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static JFrame frame = new JFrame("Hertel-Mehlhorn Convex Decomposition");
    public static GraphPanel gpanel;

    enum PhaseType {DRAW, TRIANGLE, FINAL}
    public static PhaseType phase = PhaseType.DRAW;

    /**
     * Main method of the program
     * @param args args
     */
    public static void main(String[] args)
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(0);
        frame.setLayout(borderLayout);
        frame.setSize(1200,600);
        gpanel = new GraphPanel();
        gpanel.setPreferredSize(new Dimension(700,400));
        frame.add(gpanel, BorderLayout.CENTER);
        InfoPanel infoPanel = new InfoPanel();
        infoPanel.setPreferredSize(new Dimension(1200,100));
        frame.add(infoPanel,BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * Activated when the CONTINUE button is pressed, advances to the next Stage.
     */
    public static void phaseAdd()
    {
        phase = PhaseType.values()[(phase.ordinal()+1)%3];
        if (phase == PhaseType.DRAW) {
            gpanel.polygon = new SimplePolygon(gpanel.vertices);
            gpanel.edges = new ArrayList<>();
        }
        if (phase == PhaseType.TRIANGLE)
        {
            gpanel.edges = gpanel.polygon.triangulate();
            gpanel.polygon.incorporate();
        }
        if (phase == PhaseType.FINAL)
        {
            gpanel.decompose();
        }
        gpanel.repaint();
    }

    /**
     * Activated when the CLEAR button is pressed, gets rid of the polygon and returns to DRAW Stage.
     */
    public static void phaseClear() {
        gpanel.polygon = new SimplePolygon(new Vertex[]{});
        gpanel.vertices = new ArrayList<>();
        gpanel.edges = new ArrayList<>();
        gpanel.repaint();
        phase = PhaseType.DRAW;
    }
}
