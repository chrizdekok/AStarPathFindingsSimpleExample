package com.hindelid.demo.pathfindingtest;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class TestNode implements IndexedNode<TestNode> {

	public final static int TILE_SIZE = 50;
	
	/** Index that needs to be unique for every node and starts from 0. */
	private int mIndex;
	
	/** Whether or not this tile is in the path. */
	private boolean mSelected = false;
	
	/** X pos of node. */
	public final int mX;
	/** Y pos of node. */
	public final int mY;
	
	/** The neighbours of this node. i.e to which node can we travel to from here. */ 
	Array<Connection<TestNode>> mConnections = new Array<Connection<TestNode>>();
	
	/** @param aIndex needs to be unique for every node and starts from 0. */
	public TestNode(int aX, int aY, int aIndex) {
		mIndex = aIndex;
		mX = aX;
		mY = aY;
	}
	
	@Override
	public int getIndex() {
		return mIndex;
	}

	@Override
	public Array<Connection<TestNode>> getConnections() {
		return mConnections;
	}
	
	public void addNeighbour(TestNode aNode) {
	    if (null != aNode) {
	        mConnections.add(new DefaultConnection<TestNode>(this, aNode));
	    }
	}
	
	public void select() {
	    mSelected = true;
	}
	
	/** Render this tile as a white square. */
	public void render(ShapeRenderer aShapeRenderer) {
		if (mSelected) {
		    aShapeRenderer.setColor(Color.WHITE);
		} else {
		    aShapeRenderer.setColor(Color.RED);
		}
		aShapeRenderer.line(mX, mY, mX, mY + TILE_SIZE);
        aShapeRenderer.line(mX, mY, mX + TILE_SIZE, mY);
        aShapeRenderer.line(mX, mY + TILE_SIZE, mX + TILE_SIZE, mY + TILE_SIZE);
        aShapeRenderer.line(mX + TILE_SIZE, mY, mX + TILE_SIZE, mY + TILE_SIZE);
	}

}
