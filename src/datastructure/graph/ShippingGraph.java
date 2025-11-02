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
        adjacencyList.putIfAbsent(from, new HashMap<>());
        adjacencyList.putIfAbsent(to, new HashMap<>());
        adjacencyList.get(from).put(to, distance);
        adjacencyList.get(to).put(from, distance);
    }

    public Map<String, Integer> dijkstra(String start) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        for (String city : adjacencyList.keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            String currentCity = current.city;

            if (visited.contains(currentCity)) continue;
            visited.add(currentCity);

            if (!adjacencyList.containsKey(currentCity)) continue;

            for (Map.Entry<String, Integer> neighbor : adjacencyList.get(currentCity).entrySet()) {
                String nextCity = neighbor.getKey();
                int weight = neighbor.getValue();
                int newDist = distances.get(currentCity) + weight;

                if (newDist < distances.get(nextCity)) {
                    distances.put(nextCity, newDist);
                    previous.put(nextCity, currentCity);
                    pq.offer(new Node(nextCity, newDist));
                }
            }
        }

        return distances;
    }

    public List<String> getPath(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        for (String city : adjacencyList.keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            String currentCity = current.city;

            if (visited.contains(currentCity)) continue;
            visited.add(currentCity);

            if (currentCity.equals(end)) break;

            if (!adjacencyList.containsKey(currentCity)) continue;

            for (Map.Entry<String, Integer> neighbor : adjacencyList.get(currentCity).entrySet()) {
                String nextCity = neighbor.getKey();
                int weight = neighbor.getValue();
                int newDist = distances.get(currentCity) + weight;

                if (newDist < distances.get(nextCity)) {
                    distances.put(nextCity, newDist);
                    previous.put(nextCity, currentCity);
                    pq.offer(new Node(nextCity, newDist));
                }
            }
        }

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
