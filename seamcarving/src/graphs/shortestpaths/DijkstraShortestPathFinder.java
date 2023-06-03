package graphs.shortestpaths;

import priorityqueues.ArrayHeapMinPQ;
import priorityqueues.ExtrinsicMinPQ;
import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.NaiveMinPQ;

import java.util.*;

import java.util.Map;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new ArrayHeapMinPQ<>();
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {

        HashMap<V, E> spt = new HashMap<>();
        if (Objects.equals(start, end)) {
            return spt;
        }

        Map<V, Double> distTo = new HashMap<>();
        ExtrinsicMinPQ<V> close = createMinPQ();
        Set<V> known = new HashSet<>();

        distTo.put(start, 0.0);
        close.add(start, 0.0);

        while (!close.isEmpty() && !known.contains(end)) {
            V curr = close.removeMin();
            known.add(curr);
            for (E edge : graph.outgoingEdgesFrom(curr)) {
                if (!distTo.containsKey(edge.to())) {
                    distTo.put(edge.to(), distTo.get(edge.from()) + edge.weight());
                    spt.put(edge.to(), edge);
                } else {
                    double oldDist = distTo.get(edge.to());
                    double newDist = distTo.get(edge.from()) + edge.weight();
                    if (newDist < oldDist) {
                        distTo.put(edge.to(), newDist);
                        spt.put(edge.to(), edge);
                    }
                }
                if (!close.contains(edge.to()) && !known.contains(edge.to())) {
                    close.add(edge.to(), distTo.get(edge.to()));
                }
            }
        }
        return spt;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }

        if (!spt.containsKey(end)) {
            return new ShortestPath.Failure<>();
        }

        List<E> sp = new ArrayList<>();
        V curr = end;
        while (!Objects.equals(curr, start)) {
            sp.add(0, spt.get(curr));
            curr = spt.get(curr).from();
        }

        return new ShortestPath.Success<>(sp);
    }
}
