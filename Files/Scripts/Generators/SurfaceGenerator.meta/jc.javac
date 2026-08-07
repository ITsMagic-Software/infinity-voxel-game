package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author */
public class SurfaceGenerator extends Generator {

  @Override
  public int getBlockType(int x, int y, int z, int groundHeight, GenData data) {
    if (y > groundHeight) {
      return Blocks.AIR;
    }

    int biome = data.getBiome(x, z, groundHeight);
    int depth = groundHeight - y;

    if (biome == BiomeGenerator.BEACH || biome == BiomeGenerator.DESERT) {
      if (depth <= data.beachDepth || y >= groundHeight - data.dirtLayerHeight) {
        return Blocks.SAND;
      }
      return Blocks.ROCK;
    }

    if (biome == BiomeGenerator.MOUNTAINS && depth <= 1) {
      return Blocks.ROCK;
    }

    if (depth == 0) {
      return Blocks.GRASS;
    }
    if (depth <= data.dirtLayerHeight) {
      return Blocks.DIRT;
    }
    return Blocks.ROCK;
  }
}
