package com.bc.parcor.conv;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.bc.common.core.AGI;
import com.bc.path.Node;

public class NodeTest extends TestCase {

	public void testPearsonCorrelation() {
		Node i = new Node(AGI.createAGI("AT1G00000"), new Double[]{0.775318586,0.675892628,0.619178561,0.720985147,0.812204883});
		Node j = new Node(AGI.createAGI("AT1G00001"), new Double[]{0.712070237,0.679137952,0.620678799,0.74791477,1.002128407});
		Node k1 = new Node(AGI.createAGI("AT1G00002"), new Double[]{2.065186078,1.581832636,1.04934916,1.950217008,2.998605525});
		Node k2 = new Node(AGI.createAGI("AT1G00003"), new Double[]{0.952195285,0.841016069,0.727694938,1.002632183,1.402218114});

		List<Node> ks = new ArrayList<Node>();
		ks.add(i);
		ks.add(j);
		ks.add(k1);
		ks.add(k2);
		
		Assert.assertTrue(i.calculateCorrelation(j).toString().startsWith("0.8232"));
		Assert.assertTrue(i.calculatePartialCorrelation(j, k1).toString().startsWith("-0.9261"));
		Assert.assertTrue(i.calculatePartialCorrelation(j, k2).toString().startsWith("-0.8375"));
		
		Assert.assertTrue(i.calculateWorstPartialCorrelation(j, ks).toString().startsWith("0.8375"));
	}
}
