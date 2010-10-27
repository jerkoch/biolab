package com.bc.path;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;

public class Pathway {

	private String name;
	private List<Step> steps = new ArrayList<Step>();

	public Pathway(String name) {
		this.name = name;
	}

	public void addStep(Step step) {
		steps.add(step);
	}

	public List<Node> findBestPath() {
		/*
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
		*/
		//annotate each node with score and best path
		//find first node
		List<Step> annotated = new ArrayList<Step>();
		List<Step> endSteps = new ArrayList<Step>();
		for (int i = 0; i < steps.size(); i++) {
			if (steps.get(i).getOrder().size() == 1) {	//first step
				Step firstStep = steps.get(i);
				firstStep.annotatePath(null);
				annotated.add(firstStep);
				break;
			}
		}
		while(!annotated.isEmpty()) {
			Step lastStep = annotated.get(0);
			annotated.remove(0);
			boolean endStep = true;
			//Get all steps immediately following nextStep
			for (int i = 0; i < steps.size(); i++) {
				if (steps.get(i).getOrder().size() == (lastStep.getOrder().size() + 1)) {
					boolean match = true;
					for (int j = 0; j < lastStep.getOrder().size(); j++) {
						if (!steps.get(i).getOrder().get(j).equals(lastStep.getOrder().get(j))) {
							match = false;
							break;
						}
					}
					if (match) {
						steps.get(i).annotatePath(lastStep);
						annotated.add(steps.get(i));
						endStep = false;
					}
				}
			}
			if (endStep) {
				endSteps.add(lastStep);
			}
		}
		
		//find longest end node in last steps
		Node bestNode = null;
		for (Step step : endSteps) {
			for (Node node : step.getNodes()) {
				if ((bestNode == null) || (node.getBestPath().getScore() > bestNode.getBestPath().getScore())) {
					bestNode = node;
				}
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
