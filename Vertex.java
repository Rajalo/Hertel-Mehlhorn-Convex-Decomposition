import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Vertex {
    private final int x,y;
    private Vertex next,prev;
    private Color color;
    private final ArrayList<Edge> edges;
    /**
     * Constructs a vertex
     * @param x the x-coord of the vertex
     * @param y the y-coord of the vertex
     */
    public Vertex(int x,int y)
    {
        this.x = x;
        this.y = y;
        color = new Color(170,170,170);
        edges = new ArrayList<>();
    }

    /**
     * Makes an array with the coords of the vertex
     * @return integer array [x,y]
     */
    public int[] getCoordsArr()
    {
        return new int[] {x,y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vertex getNext() {
        return next;
    }
    public Vertex getPrev() {
        return prev;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public void setNext(Vertex next) {
        this.next = next;
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }

    /**
     * Swaps the previous and next vertices.
     */
    public void invert() {
        Vertex temp = this.prev;
        this.prev = this.next;
        this.next = temp;
    }
    public void addEdge(Edge edge)
    {
        if (edges.contains(edge))
            return;
        edges.add(edge);
    }
    public void removeEdge(Edge edge)
    {
        edges.remove(edge);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return x == vertex.x &&
                y == vertex.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * Returns the sine of the angle between two edges that share this Vertex
     * @return the sine of the angle they make.
     */
    public double sinAngle(Edge firstEdge, Edge secondEdge)
    {
        int crossProduct = SimplePolygon.crossProduct(this.getCoordsArr(),firstEdge.getOther(this).getCoordsArr(),this.getCoordsArr(),secondEdge.getOther(this).getCoordsArr());
        return crossProduct/firstEdge.length()/secondEdge.length();
    }
    /**
     * Returns the sine of the angle between two edges that share this Vertex
     * @return the sine of the angle they make.
     */
    public double sinAngle(Vertex firstVertex, Vertex secondVertex)
    {
        int crossProduct = SimplePolygon.crossProduct(this.getCoordsArr(),firstVertex.getCoordsArr(),this.getCoordsArr(),secondVertex.getCoordsArr());
        return crossProduct/distance(firstVertex)/distance(secondVertex);
    }

    /**
     * Returns the distance between this instance and another Vertex
     * @param other the other vertex
     * @return the distance to the other vertex
     */
    public double distance(Vertex other)
    {
        return Math.hypot(this.x-other.x,this.y-other.y);
    }
}
