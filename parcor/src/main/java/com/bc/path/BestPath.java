package com.bc.path;

import java.util.ArrayList;
import java.util.List;

public class BestPath {

	private Double score = 0D;

	private List<Node> path;

	public void storeBestNode(Node previousNode, Node newNode, Double score) {
		this.score = score;
		if (previousNode != null) {
			path = new ArrayList<Node>(previousNode.getBestPath().getPath());
		} else {
			path = new ArrayList<Node>();
		}
		path.add(newNode);
	}

	public Double getScore() {
		return score;
	}

	public List<Node> getPath() {
		return path;
	}
}
