import java.awt.*;
import java.util.Objects;

public class Edge {
    private Vertex start,end;
    private final int[] center;

    /**
     * Constructs an edge from two vertices
     * @param start the first vertex
     * @param end the second vertex
     */
    public Edge(Vertex start, Vertex end)
    {
        this.start = start;
        this.end=end;
        center = new int[]{(start.getX()+end.getX())/2,(start.getY()+end.getY())/2};
    }

    /**
     * Constructs an Edge for use in a Simple polygon, basically sets them as next to each other
     * @param start start vertex
     * @param end end vertex
     * @return the edge from start to end
     */
    public static Edge polygonalEdge(Vertex start, Vertex end)
    {
        Edge edge = new Edge(start,end);
        start.setNext(end);
        end.setPrev(start);
        return edge;
    }
    /**
     * Constructs an Edge for use in a Simple polygon, basically sets them as next to each other
     * @param start start vertex
     * @param end end vertex
     */
    public static void orderVertex(Vertex start, Vertex end)
    {
        start.setNext(end);
        end.setPrev(start);
    }

    /**
     * Checks if an edge has a vertex as its start or end
     * @param vertex the vertex in question
     * @return true if an edge has a vertex as its start or end, false otherwise
     */
    public boolean contains(Vertex vertex)
    {
        return start.equals(vertex) || end.equals(vertex);
    }

    public Vertex getEnd() {
        return end;
    }

    public Vertex getStart() {
        return start;
    }

    /**
     * Inverts which is the start and end vertices
     */
    public void invert()
    {
        Vertex temp = start;
        start = end;
        end =temp;
    }

    @Override
    public String toString() {
        return Main.gpanel.edges.indexOf(this)+":("+Main.gpanel.vertices.indexOf(start)+","+Main.gpanel.vertices.indexOf(end)+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(start, edge.start) &&
                Objects.equals(end, edge.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    /**
     * Paints the edge in the GPanel
     * @param g the Graphics Object for GPanel
     * @param i index of the edge
     */
    public void paint(Graphics g, int i) {
        g.setColor(Color.BLACK);
        g.drawLine(start.getX(),start.getY(),end.getX(),end.getY());
        g.drawString(""+i,center[0]+1,center[1]);
    }

    public int[] getCenter() {
        return center;
    }

    /**
     * Gets the opposite vertex from the one given between the start or end vertex
     * @param vertex the vertex in considertation
     * @return the opposite vertex from the given
     */
    public Vertex getOther(Vertex vertex) {
        if (vertex.equals(start))
        {
            return end;
        }
        return start;
    }

    /**
     * Finds the Euclidean length of the Edge
     * @return the length of the Edge
     */
    public double length() {
        return Math.hypot(start.getX()-end.getX(),start.getY()-end.getY());
    }

    /**
     * Adds this edge to the edgeLists of the vertices it is composed from
     */
    public void incorporate()
    {
        start.addEdge(this);
        end.addEdge(this);
    }
}
