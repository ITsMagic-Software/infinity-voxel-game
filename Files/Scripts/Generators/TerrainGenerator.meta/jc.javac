package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author */
public class TerrainGenerator extends Component implements VoxelGeneratorListener {

  private List<Generator> generators = new ArrayList();

  @Order(idx = {0})
  public int grassLayerHeight = 3;

  @Order(idx = {1})
  public int minimalGroundHeight = 30;

  @Order(idx = {2})
  public int maxHillHeight = 25;

  @Order(idx = {3})
  public int baseHillWidth = 30;

  @Order(idx = {4})
  public int seed = 0;

  private GenData genData = new GenData();
  private boolean started = false;
  @Singleton
  private Blocks blocks;

  @Override
  public void start() {
    if (!started) {
      genData.dirtLayerHeight = grassLayerHeight;
      genData.beachLevel = minimalGroundHeight + 4;
      genData.seed = seed;
      genData.start();
      generators.add(new CaveGenerator());
      generators.add(new TreeGenerator());
      generators.add(new SurfaceGenerator());

      for (Generator gen : generators) {
        gen.start();
      }

      started = true;
    }
  }

  @Override
  public void storeChunk(OH3LevelIntArray chunk, int x, int z) {
    start();
  }

  @Override
  public OH3LevelIntArray loadChunk(int x, int z) {
    start();
    return null;
  }

  @Override
  public int getGroundHeight(int x, int z) {
    start();
    return genData.getGroundHeight(x, z, minimalGroundHeight, maxHillHeight, baseHillWidth);
  }

  @Override
  public int getBlockType(int x, int y, int z, int groundHeight) {
    start();

    for (int a = 0; a < generators.size(); a++) {
      Generator g = generators.get(a);
      int t = g.getBlockType(x, y, z, groundHeight, genData);
      if (t >= 0) {
        return t;
      }
    }

    return Blocks.AIR;
  }

  @Override
  public boolean drawFace(int blockType, int sideBlockType, VoxelGeneratorListener.Direction sideFace, Point3 internalCoords) {
    if (sideBlockType == Blocks.LEAVES) return true;

    return sideBlockType == Blocks.AIR;
  }

  @Override
  public int getTextureIndex(int blockType, VoxelGeneratorListener.Direction dir, VoxelGeneratorListener.Direction face) {
    try {
      Block block = blocks.blocks.get(blockType);
      switch (face) {
        case UP:
          return block.topFace;
        case DOWN:
          return block.bottomFace;
        default:
          return block.sideFace;
      }
    } catch (Exception e) {
      Terminal.log(e.getMessage());
      return (16*16)-1;
    } 
  }
}
