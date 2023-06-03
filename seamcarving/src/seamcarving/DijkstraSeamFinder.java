package seamcarving;

import graphs.Edge;
import graphs.Graph;
import graphs.shortestpaths.DijkstraShortestPathFinder;
import graphs.shortestpaths.ShortestPath;
import graphs.shortestpaths.ShortestPathFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DijkstraSeamFinder implements SeamFinder {
    private final ShortestPathFinder<Graph<SeamVertex, Edge<SeamVertex>>, SeamVertex, Edge<SeamVertex>> pathFinder;

    private SeamVertex[][] vertices;
    private SeamVertex end;

    public DijkstraSeamFinder() {
        this.pathFinder = createPathFinder();
    }


    protected <G extends Graph<V, Edge<V>>, V> ShortestPathFinder<G, V, Edge<V>> createPathFinder() {
        /*
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
        */
        return new DijkstraShortestPathFinder<>();
    }

    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        vertices = new SeamVertex[energies[0].length][energies.length];

        for (int i = 0; i < energies.length; i++) {
            for (int j = 0; j < energies[0].length; j++) {
                vertices[j][i] = new SeamVertex(i, j, energies[i][j]);
            }
        }
        System.out.println(Arrays.deepToString(vertices));

        end = new SeamVertex(vertices[0].length, -1, 0);
        SeamVertex start = new SeamVertex(-1, -1, 0);

        List<SeamVertex> sp = pathFinder.findShortestPath(new SeamGraph(), start, end).vertices();
        List<Integer> retList = new ArrayList<>();

        for (SeamVertex vert : sp) {
            retList.add(vert.y);
        }
        System.out.print(retList);
        retList.remove(0);
        retList.remove(retList.size() - 1);

        return retList;
    }

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        return null;
    }

    private class SeamGraph implements graphs.Graph<SeamVertex, Edge<SeamVertex>> {
        public Collection<Edge<SeamVertex>> outgoingEdgesFrom(SeamVertex vertex) {
            Set<Edge<SeamVertex>> outGoing = new HashSet<>();
            int y = vertex.y;
            int x = vertex.x;
            if (x == -1) {
                for (SeamVertex[] seamVertices : vertices) {
                    outGoing.add(new Edge<>(vertex, seamVertices[0], 0));
                }
                return outGoing;
            } else if (x + 1 < vertices[0].length) {
                outGoing.add(new Edge<>(vertex, vertices[y][x + 1], vertices[y][x + 1].weight));
                if (y - 1 > -1) {
                    outGoing.add(new Edge<>(vertex, vertices[y - 1][x + 1], vertices[y - 1][x + 1].weight));
                }
                if (y + 1 < vertices.length) {
                    outGoing.add(new Edge<>(vertex, vertices[y + 1][x + 1], vertices[y + 1][x + 1].weight));
                }
            } else if (x + 1 == vertices[0].length) {
                outGoing.add(new Edge<>(vertex, end, 0));
            }

            return outGoing;
        }
    }

    public static class SeamVertex {
        public int x;
        public int y;
        public double weight;

        public SeamVertex(int x, int y, double weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }

        public boolean equals(Object other) {
            if (!(other instanceof SeamVertex)) {
                return false;
            }
            SeamVertex o = (SeamVertex) other;
            return this.x == o.x && this.y == o.y;
        }

        public int hashCode() {
            return Objects.hash(10 * x + y);
        }

        public String toString() {
            return "x: " + x + ", y: " + y;
        }
    }
}
