/*
 * Copyright 2018 DrJavaSaurus <javasaurusdev@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package control.sprite;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.texture.Texture2D;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import jmonkey2D.control.GameObjectPool;
import jmonkey2D.model.GameObject;
import jmonkey2D.model.physics.RigidBody2D;
import jmonkey2D.model.sprites.Sprite;
import jmonkey2D.model.sprites.data.MapData;
import jmonkey2D.model.sprites.data.SpriteData;
import org.dyn4j.geometry.Rectangle;

/**
 *
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 */
public class TileManager {

    /**
     * The tile texture
     */
    private Texture2D tileTexture;

    /**
     * The asset manager to use and load textures
     */
    private final AssetManager assetManager;

    /**
     * The singleton instance (we don't want multiple caches, that defies the
     * point here)
     */
    private static TileManager INSTANCE;
    private SpriteData[] spriteData;
    private int columns;
    private int rows;

    /**
     * The private constructor
     *
     * @param assetManager the assetmanager to be used
     */
    private TileManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    /**
     *
     * @param assetManager to be registered from the SimpleApplication
     * @return the singleton instance
     */
    public static TileManager getInstance(AssetManager assetManager) {
        if (INSTANCE == null) {
            INSTANCE = new TileManager(assetManager);
        }
        return INSTANCE;
    }

    /**
     *
     * @return the material needed for the sprite rendering
     */
    public Material GetSpriteMaterial() {
        return assetManager.loadMaterial("SpriteRendering/SpriteMaterial.j3m");
    }

    /**
     * Loads a tileset in memory
     *
     * @param spriteSheetPath the sprite sheet path
     * @param tileWidth the width of a tile (in pixels)
     * @param tileHeight the height of a tile (in pixels)
     */
    public void LoadTileSet(String spriteSheetPath, int tileWidth, int tileHeight) {
        tileTexture = getTexture2D(spriteSheetPath);
        columns = tileTexture.getImage().getWidth() / tileWidth;
        rows = tileTexture.getImage().getHeight() / tileHeight;
        spriteData = new SpriteData[columns * rows];
        int index = 0;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                spriteData[index] = new SpriteData(columns, rows, index);
                index++;
            }
        }
    }

    /**
     * Retrieve a tile from the tilesheet
     *
     * @param index the tile index
     * @param gameObjectPool
     * @return a sprite
     */
    public GameObject getTile(int index, GameObjectPool gameObjectPool) {
        if (index >= spriteData.length) {
            return null;
        }
        GameObject tmp = gameObjectPool.CreateGameObject();
        Sprite tileSprite = tmp.addSprite("Tile_" + index, tileTexture, spriteData[index]);
        RigidBody2D tileBody = tmp.addRigidBody2D(true);
        tileBody.removeAllFixtures();
        tileBody.addFixture(new Rectangle(1, 1));
        return tmp;
    }

    /**
     * Loads an array of indices onto the map
     *
     * @param mapData the map data
     * @param gameObjectPool the objectPool
     * @param mapNode the node to attach the tiles to
     */
    public void LoadMap(MapData mapData, GameObjectPool gameObjectPool, Node mapNode) {
        int[][] tileIndexes = mapData.getMapArray();
        for (int i = 0; i < tileIndexes.length; i++) {
            for (int j = 0; j < tileIndexes[i].length; j++) {
                int tileIndex = tileIndexes[i][j];
                if (tileIndex > 0) {
                    GameObject tile = getTile(tileIndex, gameObjectPool);

                    if (tile != null) {
                        Sprite sprite = tile.addSprite("Tile_"+i+"_"+j, tileTexture, spriteData[tileIndex]);
                        Entity entity = gameObjectPool.getEntity(tile.getEntityID(), RigidBody2D.class);
                        RigidBody2D body = entity.get(RigidBody2D.class);
                        if (j > 0) {
                            body.translate(i, j - 0.01f);
                        } else {
                            body.translate(i, j);
                        }
                        mapNode.attachChild(sprite);
                    }
                }
            }
        }
    }

    /**
     * Use this method to get a sprite through the textureCache
     *
     * @param spritePath the path to the spritesheet (example
     * "Textures/MySpriteSheet.png");
     * @return the Texture2D for the given spritesheet
     */
    private Texture2D getTexture2D(String spritePath) {
        return (Texture2D) assetManager.loadTexture(new TextureKey(spritePath, false));
    }

}
