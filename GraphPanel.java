import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Shows the left side of the screen with the polygon
 */
public class GraphPanel extends JPanel implements MouseListener, KeyListener {
    SimplePolygon polygon;
    ArrayList<Edge> edges;
    ArrayList<Vertex> vertices;

    static int pointerX,pointerY;
    public GraphPanel()
    {
        setBackground(Color.white);
        addMouseListener(this);
        setFocusable(true);
        addKeyListener(this);
        polygon = new SimplePolygon(new Vertex[]{});
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        repaint();
        pointerX = pointerY = -10;
    }
    /**
     * Paints the left side of the screen
     * @param g Graphics object used by JPanel
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        polygon.paint(g);
        if (Main.phase != Main.PhaseType.DRAW)
        {
            int i = 0;
            for (Edge edge : edges) {
                edge.paint(g, i++);
            }
        }
    }

    /**
     * Given the triangulation, finds a convex decomposition
     */
    public void decompose()
    {
        for (Vertex vertex : vertices)
        {
            vertex.getEdges().sort((o1, o2) -> {
                if ((o1.getCenter()[1]-vertex.getY())*(o2.getCenter()[1]-vertex.getY())<0)
                    return o1.getCenter()[1]-o2.getCenter()[1];
                int crossproduct= SimplePolygon.crossProduct(vertex.getCoordsArr(),o1.getCenter(),vertex.getCoordsArr(),o2.getCenter());
                return Integer.compare(crossproduct, 0);
            });
        }
        for (int i = 0; i < edges.size()&&edges.size()!=0;i++)
        {
            Edge edge = edges.get(i);
            if (vertexClearance(edge,edge.getEnd())&&vertexClearance(edge,edge.getStart()))
            {
                removeEdge(edge);
                i--;
            }
        }
    }

    /**
     * Checks if a diagonal can be removed based off info at given vertex
     * @param edge the diagonal potentially to be removed
     * @param vertex the vertex being checked
     * @return true if the diagonal would keep all angles of the vertex are convex, false otherwise
     */
    public static boolean vertexClearance(Edge edge, Vertex vertex)
    {
        ArrayList<Edge> edges = vertex.getEdges();
        if (edges.size()==3)
        {
            return vertex.sinAngle(vertex.getPrev(),vertex.getNext())<=0;
        }
        Edge prev = edges.get((edges.indexOf(edge)-1+edges.size())% edges.size());
        Edge next = edges.get((edges.indexOf(edge)+1)% edges.size());
        return vertex.sinAngle(prev,next)<=0;
    }

    /**
     * Removes an edge from the program
     * @param edge the edge being removed
     */
    public void removeEdge(Edge edge)
    {
        edge.getStart().removeEdge(edge);
        edge.getEnd().removeEdge(edge);
        edges.remove(edge);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    /**
     * Determines what to do when mouse is pressed
     * @param e KeyEvent containing info on which mouse button was pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (Main.phase == Main.PhaseType.DRAW) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                vertices.add(new Vertex(e.getX(), e.getY()));
                polygon = new SimplePolygon(vertices);
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                Vertex closest = vertices.get(0);
                double dist = Math.hypot(closest.getX() - e.getX(), closest.getY() - e.getY());
                for (int i = 1; i < vertices.size(); i++) {
                    Vertex vertex = vertices.get(i);
                    if (Math.hypot(vertex.getX() - e.getX(), vertex.getY() - e.getY()) < dist) {
                        dist = Math.hypot(vertex.getX() - e.getX(), vertex.getY() - e.getY());
                        closest = vertex;
                    }
                }
                vertices.remove(closest);
                polygon = new SimplePolygon(vertices);
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}