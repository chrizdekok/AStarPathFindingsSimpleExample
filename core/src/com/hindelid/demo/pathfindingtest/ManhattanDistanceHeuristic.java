package com.hindelid.demo.pathfindingtest;

import com.badlogic.gdx.ai.pfa.Heuristic;

public class ManhattanDistanceHeuristic implements Heuristic<TestNode> {

    @Override
    public float estimate(TestNode node, TestNode endNode) {
        return Math.abs(endNode.mX - node.mX) + Math.abs(endNode.mY - node.mY);
    }

}