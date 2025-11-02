package datastructure.graph;

import java.util.*;

public class ShippingGraph {
    private Map<String, Map<String, Integer>> adjacencyList;

    public ShippingGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addCity(String city) {
        adjacencyList.putIfAbsent(city, new HashMap<>());
    }

    public void addRoute(String from, String to, int distance) {
        /**
         * Ensure both cities exist in the graph
         */
        adjacencyList.putIfAbsent(from, new HashMap<>());
        adjacencyList.putIfAbsent(to, new HashMap<>());

        /**
         * Add bidirectional route
         */
        adjacencyList.get(from).put(to, distance);
        adjacencyList.get(to).put(from, distance);
    }

    /**
     * Dijkstra's algorithm to find shortest paths from start city
     * 
     * @param start
     * @return Map of cities to their shortest distance from start
     */
    public Map<String, Integer> dijkstra(String start) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        /**
         * Set initial distances to infinity (MAX_VALUE)
         */
        for (String city : adjacencyList.keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }

        /**
         * Set distance to start city as 0 and add to priority queue
         */
        distances.put(start, 0);
        pq.offer(new Node(start, 0));

        /**
         * Keep processing until queue is empty
         */
        while (!pq.isEmpty()) {
            /**
             * Get node with smallest distance
             */
            Node current = pq.poll();
            String currentCity = current.city;

            /**
             * Skip if already visited
             */
            if (visited.contains(currentCity))
                continue;

            /**
             * Mark current city as visited
             */
            visited.add(currentCity);

            /**
             * Skip if current city has no neighbors
             */
            if (!adjacencyList.containsKey(currentCity))
                continue;

            /**
             * Loop through neighbors and update distances
             */
            for (Map.Entry<String, Integer> neighbor : adjacencyList.get(currentCity).entrySet()) {
                String nextCity = neighbor.getKey();
                int weight = neighbor.getValue();
                int newDist = distances.get(currentCity) + weight;

                /**
                 * If new distance is shorter, update and add to queue
                 */
                if (newDist < distances.get(nextCity)) {
                    distances.put(nextCity, newDist);
                    previous.put(nextCity, currentCity);
                    pq.offer(new Node(nextCity, newDist));
                }
            }
        }

        return distances;
    }

    /**
     * Get shortest path from start to end city
     * 
     * @param start
     * @param end
     * @return List of cities representing the path
     */
    public List<String> getPath(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        /**
         * Set initial distances to infinity (MAX_VALUE)
         */
        for (String city : adjacencyList.keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }

        /**
         * Set distance to start city as 0 and add to priority queue
         */
        distances.put(start, 0);
        pq.offer(new Node(start, 0));

        /**
         * Keep processing until queue is empty
         */
        while (!pq.isEmpty()) {
            /**
             * Get node with smallest distance
             */
            Node current = pq.poll();
            String currentCity = current.city;

            /**
             * Skip if already visited
             */
            if (visited.contains(currentCity))
                continue;
            visited.add(currentCity);

            /**
             * If reached end city, stop
             */
            if (currentCity.equals(end))
                break;

            /**
             * Skip if current city has no neighbors
             */
            if (!adjacencyList.containsKey(currentCity))
                continue;

            /**
             * Loop through neighbors and update distances
             */
            for (Map.Entry<String, Integer> neighbor : adjacencyList.get(currentCity).entrySet()) {
                String nextCity = neighbor.getKey();
                int weight = neighbor.getValue();
                int newDist = distances.get(currentCity) + weight;

                /**
                 * If new distance is shorter, update and add to queue
                 */
                if (newDist < distances.get(nextCity)) {
                    distances.put(nextCity, newDist);
                    previous.put(nextCity, currentCity);
                    pq.offer(new Node(nextCity, newDist));
                }
            }
        }

        /**
         * Reconstruct path from end to start
         */
        List<String> path = new ArrayList<>();
        String current = end;
        if (!previous.containsKey(end) && !start.equals(end)) {
            return path;
        }
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }
        return path;
    }

    public Set<String> getAllCities() {
        return adjacencyList.keySet();
    }

    public Map<String, Map<String, Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    private static class Node implements Comparable<Node> {
        String city;
        int distance;

        public Node(String city, int distance) {
            this.city = city;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}
