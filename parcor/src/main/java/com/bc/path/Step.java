package com.bc.path;

import java.util.ArrayList;
import java.util.List;

import com.bc.common.core.AGI;

public class Step {

	private String reactionId;
	private String description;

	private ExpressionMap expressionMap;

	private List<Node> nodes = new ArrayList<Node>();
	
	public Step(String reactionId, String description) {
		this(null, reactionId, description);
	}

	public Step(ExpressionMap expressionMap, String reactionId, String description) {
		this.expressionMap = expressionMap;
		this.reactionId = reactionId;
		this.description = description;
	}

	public void addNode(AGI agi) {
		Node node = new Node(agi, expressionMap.getExpressionData(agi));
		nodes.add(node);
	}

	public String getReactionId() {
		return reactionId;
	}

	public String getDescription() {
		return description;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public Double[][] calculateMatrix(Step B) {
		// for consistency, refer to this as A
		Step A = this;

		// create the list of ks
		List<Node> ks = new ArrayList<Node>();
		ks.addAll(A.getNodes());
		ks.addAll(B.getNodes());

		Double[][] m = new Double[A.getNodes().size()][B.getNodes().size()];
		for (int i = 0; i < A.getNodes().size(); i++) {
			for (int j = 0; j < B.getNodes().size(); j++) {
				Node node_i = A.getNodes().get(i);
				Node node_j = B.getNodes().get(j);
				Double val = node_i.calculateWorstPartialCorrelation(node_j, ks);
				m[i][j] = val != Double.POSITIVE_INFINITY ? val : 0;
			}
		}
		return m;
	}
	
	public void annotatePath(Step A) {
		Step B = this;
		if (A != null) {
			Double[][] matrix = A.calculateMatrix(this);
			for (int j = 0; j < B.getNodes().size(); j++) {
				Double max = 0.0;
				Node node = A.getNodes().get(0);
				for (int i = 0; i < A.getNodes().size(); i++) {
					Double score = A.getNodes().get(i).getBestPath().getScore() + matrix[i][j];
					if (score > max) {
						max = score;
						node = A.getNodes().get(i);
					}
				}
				B.getNodes().get(j).annotateBestPath(node, max);
			}
		} else {
			for (Node node : B.getNodes()) {
				node.annotateBestPath(null, 0D);
			}
		}
	}
	
	@Override
	public String toString() {
		return "[reactionId=" + reactionId + ",nodes=" + nodes.toString() + "]";
	}
}
