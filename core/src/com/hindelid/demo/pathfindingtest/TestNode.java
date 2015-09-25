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

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class TestNode implements IndexedNode<TestNode> {

	public final static int TILE_SIZE = 50;
	private final static int SPACE_BETWEEN_TILES = 2;
	
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
	
	/** Render this tile as a white square or red if included in the found path. */
	public void render(ShapeRenderer aShapeRenderer) {
		if (mSelected) {
		    aShapeRenderer.setColor(Color.RED);
		} else {
		    aShapeRenderer.setColor(Color.WHITE);
		}
		aShapeRenderer.line(mX, mY, mX, mY + TILE_SIZE - SPACE_BETWEEN_TILES);
        aShapeRenderer.line(mX, mY, mX + TILE_SIZE - SPACE_BETWEEN_TILES, mY);
        aShapeRenderer.line(mX, mY + TILE_SIZE - SPACE_BETWEEN_TILES, mX + TILE_SIZE - SPACE_BETWEEN_TILES, mY + TILE_SIZE - SPACE_BETWEEN_TILES);
        aShapeRenderer.line(mX + TILE_SIZE - SPACE_BETWEEN_TILES, mY, mX + TILE_SIZE - SPACE_BETWEEN_TILES, mY + TILE_SIZE - SPACE_BETWEEN_TILES);
	}

	public String toString() {
		return String.format("Index:%d x:%d y:%d", mIndex, mX, mY);
	}

}
