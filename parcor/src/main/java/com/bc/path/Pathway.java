package com.bc.path;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;

public class Pathway {

	private String name;
	private List<Step> steps = new ArrayList<Step>();;

	public Pathway(String name) {
		this.name = name;
	}

	public void addStep(Step step) {
		steps.add(step);
	}

	public List<Node> findBestPath() {
		// annotate B nodes with score and best path
		for (int i = 0; i < steps.size(); i++) {
			steps.get(i).annotatePath(i == 0 ? null : steps.get(i - 1));
		}

		// find the best (longest) node in the last step
		Node bestNode = null;
		for (Node node : steps.get(steps.size() - 1).getNodes()) {
			if (bestNode == null || node.getBestPath().getScore() > bestNode.getBestPath().getScore()) {
				bestNode = node;
			}
		}

		return bestNode.getBestPath().getPath();
	}

	@Override
	public String toString() {

		StrBuilder sb = new StrBuilder();
		sb.append("Pathway - ");
		sb.appendln(name);
		sb.appendln("Best path: " + findBestPath());
		for (Step step : steps) {
			sb.append("step:");
			sb.appendln(step);
		}
		sb.appendln("Nodes missing expression data: " + getNodesWithNoExpressionData());
		// if (steps.size() > 1) {
		// for (int i = 0; i < steps.size() - 1; i++) {
		// sb.appendln("Matrix (" + i + ")");
		// sb.appendln(getArrayOutput(steps.get(i).calculateMatrix(steps.get(i +
		// 1))));
		// }
		// }
		return sb.toString();
	}

	public List<Node> getNodesWithNoExpressionData() {
		List<Node> nodes = new ArrayList<Node>();
		for (Step step : steps) {
			for (Node node : step.getNodes()) {
				if (node.getExpressionData() == null) {
					nodes.add(node);
				}
			}
		}
		return nodes;
	}

	private String getArrayOutput(Double[][] arr) {
		StrBuilder sb = new StrBuilder();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (j != 0) {
					sb.appendSeparator(' ');
				}
				String val = StringUtils.substring(arr[i][j].toString(), 0, 5);
				sb.append(StringUtils.rightPad(val, 6));
			}
			sb.appendNewLine();
		}
		return sb.toString();
	}
}
