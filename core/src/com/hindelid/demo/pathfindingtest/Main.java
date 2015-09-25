package com.hindelid.demo.pathfindingtest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Main extends ApplicationAdapter {
    final static int MAP_SIZE = 10;

    ShapeRenderer mShapeRenderer;

    IndexedAStarPathFinder<TestNode> mPathFinder;
    TestGraph mGraph;
    DefaultGraphPath<TestNode> mPath;
    ManhattanDistanceHeuristic mHeuristic;

    /** Our array of nodes. 
     * Make sure that this has the same size as mMap. */
    TestNode[][] mNodes = new TestNode[6][6];

    /** 6x6 map where 1 is a node and 0 is not a node. */
    int[][] mMap = { 
            { 1, 1, 1, 1, 1, 0 }, 
            { 0, 0, 1, 0, 1, 0 }, 
            { 0, 1, 1, 0, 0, 0 }, 
            { 0, 0, 1, 0, 0, 1 },
            { 0, 0, 1, 1, 1, 1 }, 
            { 0, 0, 0, 0, 0, 0 } };

    @Override
    public void create() {
        mShapeRenderer = new ShapeRenderer();

        mGraph = new TestGraph(20);

        mPath = new DefaultGraphPath<TestNode>();
        mHeuristic = new ManhattanDistanceHeuristic();

        // Initialize all the nodes that should be present.
        int index = 0; //Used to set index for every node.
        for (int x = 0; x < mMap.length; x++) {
            for (int y = 0; y < mMap[0].length; y++) {
                if (mMap[x][y] == 1) {
                    mNodes[x][y] = new TestNode(x*TestNode.TILE_SIZE,y*TestNode.TILE_SIZE,index++);
                    mGraph.addNode(mNodes[x][y]);
                }
            }
        }
        
        // Add connection to every neighbour of this node. 
        for (int x = 0; x < mNodes.length; x++) {
            for (int y = 0; y < mNodes[0].length; y++) {
                if (null != mNodes[x][y]) {
                    addNodeNeighbour(mNodes[x][y], x - 1, y);
                    addNodeNeighbour(mNodes[x][y], x + 1, y);
                    addNodeNeighbour(mNodes[x][y], x - 1, y - 1);
                    addNodeNeighbour(mNodes[x][y], x - 1, y + 1);
                }
            }
        }
        
        mPathFinder = new IndexedAStarPathFinder<TestNode>(mGraph, true);
        calculatePath();
    }
             
    private void addNodeNeighbour(TestNode aNode, int aX, int aY) {
        // Make sure that we are within our array bounds. 
        if (aX >= 0 && aX < mNodes.length && aY >=0 && aY < mNodes[0].length) {
            aNode.addNeighbour(mNodes[aX][aY]);
        }
    }

    private void calculatePath() {
        TestNode startNode = mGraph.getNode(0); //Hardcoded 
        TestNode endNode = mGraph.getNode(10); //Hardcoded  
        for (Connection<TestNode> c : startNode.getConnections()) {
            System.out.println("Start Node FromCon:" + c.getFromNode().getIndex());
            System.out.println("Start Node ToCon:" + c.getToNode().getIndex());
        }
        mPath.clear();

        mPathFinder.searchNodePath(startNode, endNode, mHeuristic, mPath);

        if (mPath.nodes.size == 0) {
            System.out.print("-----No path found-----");
        } else {
            System.out.print("-----Found path-----");
        }
        for (TestNode node : mPath.nodes) {
            node.select();
            System.out.format("index:%d x:%d y:%d\n", node.getIndex(), node.mX, node.mY);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int x = 0; x < mNodes.length; x++) {
            for (int y = 0; y < mNodes[0].length; y++) {
                if (null != mNodes[x][y]) {
                    mNodes[x][y].render(mShapeRenderer);
                }
            }
        }
        mShapeRenderer.end();
    }
}
