package com.hindelid.demo.pathfindingtest;

import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;

public class TestGraph extends DefaultIndexedGraph<TestNode> {
    
	/**
	 * @param aSize Just an estimate of size. Will grow later if needed.
	 */
    public TestGraph(int aSize) {
        super(aSize);
    }

    public void addNode(TestNode aNodes) {
        nodes.add(aNodes);
    }

    public TestNode getNode(int aIndex) {
        return nodes.get(aIndex);
    }

}