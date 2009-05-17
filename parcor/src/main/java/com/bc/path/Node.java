package com.bc.path;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math.stat.regression.SimpleRegression;

import com.bc.common.core.AGI;

public class Node {

	private AGI agi;
	private Double[] expressionData;
	
	private Node path;
	
	private BestPath bestPath = new BestPath();
	
	public Node(AGI agi, Double[] expressionData) {
		this.agi = agi;
		this.expressionData = expressionData;
	}
	
	public void annotateBestPath(Node previousNode, Double score) {
		bestPath.storeBestNode(previousNode, this, score);
	}
	
	public BestPath getBestPath() {
		return bestPath;
	}
	
	public AGI getAgi() {
		return agi;
	}

	public Double[] getExpressionData() {
		return expressionData;
	}

	public Double calculateCorrelation(Node j) {
		if (expressionData == null || j.getExpressionData() == null) {
			return null;
		}
		SimpleRegression simpleRegression = new SimpleRegression();
		for (int i = 0; i < expressionData.length; i++) {
			simpleRegression.addData(expressionData[i], j.getExpressionData()[i]);
		}
		return simpleRegression.getR();
	}

	public Double calculatePartialCorrelation(Node j, Node k) {
		Double pij = calculateCorrelation(j);
		Double pik = calculateCorrelation(k);
		Double pjk = j.calculateCorrelation(k);
		if (pij != null && pik != null && pjk != null) {
			return (pij - (pik * pjk)) / (Math.sqrt(1 - Math.pow(pik, 2)) * Math.sqrt(1 - Math.pow(pjk, 2)));
		} else {
			return null;
		}
	}

	public Double calculateWorstPartialCorrelation(Node j, List<Node> ks) {
		Node i = this;
		double minCor = Double.POSITIVE_INFINITY;
		for (Node k : ks) {
			if (k != i && k != j) {
				Double parCor = calculatePartialCorrelation(j, k);
				if (parCor != null) {
					minCor = Math.min(minCor, Math.abs(parCor));
				}
			}
		}
		return minCor;
	}

	@Override
	public String toString() {
		return agi.getId() + ":" + (expressionData == null ? "nodata" : "data") + ",s=" + StringUtils.substring(bestPath.getScore().toString(), 0,4);
	}
}
