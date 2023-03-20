package control.sprite;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.texture.Texture2D;
import java.util.HashMap;

/**
 * This class should be used to load textures in a cached way (share textures
 * across objects, but use individual materials)
 *
 */
public class SpriteManager {

    /**
     * The asset manager to use and load textures
     */
    private final AssetManager assetManager;

    /**
     * A cache to keep track of currenty loaded textures so they aren't loaded
     * every sprite creation
     */
    private final HashMap<String, Texture2D> textureCache = new HashMap<>();

    /**
     * The singleton instance (we don't want multiple caches, that defies the
     * point here)
     */
    private static SpriteManager INSTANCE;

    /**
     * The private constructor
     *
     * @param assetManager the assetmanager to be used
     */
    private SpriteManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    /**
     *
     * @param assetManager to be registered from the SimpleApplication
     * @return the singleton instance
     */
    public static SpriteManager getInstance(AssetManager assetManager) {
        if (INSTANCE == null) {
            INSTANCE = new SpriteManager(assetManager);
        }
        return INSTANCE;
    }

    /**
     *
     * @return the singleton instance
     */
    public static SpriteManager getInstance() {
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
     * Clears the textureCache (needs to be called on cleanup...except when the
     * spritesheets stay in memory)
     */
    public void Clear() {
        textureCache.clear();
    }

    /**
     * Use this method to get a sprite through the textureCache
     *
     * @param spritePath the path to the spritesheet (example
     * "Textures/MySpriteSheet.png");
     * @param override boolean indicating if this spritesheet should be reloaded
     * @return the Texture2D for the given spritesheet
     */
    public Texture2D getTexture2D(String spritePath, boolean override) {
        Texture2D cachedTexture;
        if (textureCache.containsKey(spritePath)) {
            if (override) {
                cachedTexture = loadTexture2D(spritePath);
            } else {
                cachedTexture = textureCache.get(spritePath);
            }
        } else {
            cachedTexture = loadTexture2D(spritePath);
        }
        return cachedTexture;
    }

    /**
     * Use this method to get a sprite through the textureCache
     *
     * @param spritePath the path to the spritesheet (example
     * "Textures/MySpriteSheet.png");
     * @return the Texture2D for the given spritesheet
     */
    public Texture2D getTexture2D(String spritePath) {
        return getTexture2D(spritePath, false);
    }

    /**
     * Loads the texture through the assetmanager into the cache
     *
     * @param spritePaththe path to the spritesheet (example
     * "Textures/MySpriteSheet.png");
     * @return the Texture2D for the given spritesheet
     */
    private Texture2D loadTexture2D(String spritePath) {
        textureCache.put(spritePath, (Texture2D) assetManager.loadTexture(new TextureKey(spritePath, false)));
        return textureCache.get(spritePath);
    }

}
