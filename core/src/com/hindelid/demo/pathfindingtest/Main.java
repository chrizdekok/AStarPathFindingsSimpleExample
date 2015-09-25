/*******************************************************************************
 * Copyright 2014 Christoffer Hindelid.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.hindelid.demo.pathfindingtest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Main extends ApplicationAdapter {

    /** Just to be able to draw the rectangles. */
    ShapeRenderer mShapeRenderer;

    IndexedAStarPathFinder<TestNode> mPathFinder;

    TestGraph mGraph;
    /** This is where the solution will end up. */
    DefaultGraphPath<TestNode> mPath;
    /** This is a Heuristic function that will estimate
     * how close the current node is to the end. */
    ManhattanDistanceHeuristic mHeuristic;

    /** Our array of nodes. 
     * Make sure that this has the same size as mMap. */
    TestNode[][] mNodes = new TestNode[6][6];

    /** 6x6 map where 1 is a node and 0 is not a node.
     * On the screen x=0 and y=0 is the lower left corner.
     * So this matrix below will actually rotate 90 deg
     * counter clockwise when presented on screen. */
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
                    mNodes[x][y] = new TestNode(x*TestNode.TILE_SIZE,
                                                y*TestNode.TILE_SIZE,
                                                index++);

                    mGraph.addNode(mNodes[x][y]);
                }
            }
        }
        
        // Add connection to every neighbour of this node. 
        for (int x = 0; x < mNodes.length; x++) {
            for (int y = 0; y < mNodes[0].length; y++) {
                if (null != mNodes[x][y]) {
                    addNodeNeighbour(mNodes[x][y], x - 1, y); // Node to left
                    addNodeNeighbour(mNodes[x][y], x + 1, y); // Node to right
                    addNodeNeighbour(mNodes[x][y], x, y - 1); // Node below
                    addNodeNeighbour(mNodes[x][y], x, y + 1); // Node above
                }
            }
        }
        
        mPathFinder = new IndexedAStarPathFinder<TestNode>(mGraph, true);
        calculatePath();
    }
    /**
     * Add connections to the node at aX aY. If there is no node present at those
     * coordinates no connection will be created.
     * @param aNode The node to connect from.
     * @param aX x coordinate of connecting node.
     * @param aY y coordinate of connecting node.
     */
    private void addNodeNeighbour(TestNode aNode, int aX, int aY) {
        // Make sure that we are within our array bounds. 
        if (aX >= 0 && aX < mNodes.length && aY >=0 && aY < mNodes[0].length) {
            aNode.addNeighbour(mNodes[aX][aY]);
        }
    }

    private void calculatePath() {
        TestNode startNode = mNodes[0][0]; //Hardcoded for now
        TestNode endNode = mNodes[3][5]; //Hardcoded for now

        mPath.clear();

        mPathFinder.searchNodePath(startNode, endNode, mHeuristic, mPath);

        if (mPath.nodes.size == 0) {
            System.out.println("-----No path found-----");
        } else {
            System.out.println("-----Found path-----");
        }
        // Loop throw every node in the solution and select it.
        for (TestNode node : mPath.nodes) {
            node.select();
            System.out.println(node);
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
