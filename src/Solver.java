import java.util.ArrayList;
import java.util.HashSet;

/**
 * Solves the traveling salesman problem using Branch and Bound by utilizing Node's
 */
public class Solver {
	double[][] distances;
	double best_cost;
	Integer[] best_path;

	//Constructor
	public Solver(double[][] adj,int n) {
		distances = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int ii = 0; ii < n; ii++)
				distances[i][ii] =adj[i][ii];
		}
	}

	/**
	 * Calculates the shortest (non-repeating) path between a series of nodes
	 *
	 * and willl return  An array with the locations of the best path
	 */
	public Integer[] calculate() {
		HashSet<Integer> location_set = new HashSet<Integer>(distances.length);
		for(int i = 0; i < distances.length; i++)
			location_set.add(i);

		best_cost = findGreedyCost(0, location_set, distances);

		int[] active_set = new int[distances.length];
		for(int i = 0; i < active_set.length; i++)
			active_set[i] = i;

		Node root = new Node(null, 0, distances, active_set, 0);
		traverse(root);

		return best_path;
	}

	
	public double getCost() {
		return best_cost;
	}

	/**
	 * Find the greedy cost for a set of locations
	 *
	 *  i The current location
	 *  location_set Set of all remaining locations
	 *  distances The 2D array containing point distances
	 */
	private double findGreedyCost(int i, HashSet<Integer> location_set, double[][] distances) {
		if(location_set.isEmpty())
			return distances[0][i];

		location_set.remove(i);

		double lowest = Double.MAX_VALUE;
		int closest = 0;
		for(int location : location_set) {
			double cost = distances[i][location];
			if(cost < lowest) {
				lowest = cost;
				closest = location;
			}
		}

		return lowest + findGreedyCost(closest, location_set, distances);
	}

	/**
	 * Recursive method to go through the tree finding and pruning paths
	 *
	 * @param parent The root/parent node
	 */
	private void traverse(Node parent) {
		Node[] children = parent.generateChildren();

		for(Node child : children) {
			if(child.isTerminal()) {
				double cost = child.getPathCost();
				if(cost < best_cost) {
					best_cost = cost;
					best_path = child.getPath();
				}
			}
			else if(child.getLowerBound() <= best_cost) {
				traverse(child);
			}
		}
	}
}
