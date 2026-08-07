package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author */
public class TreeGenerator extends Generator {

  public int minTreeHeight = 3;
  public int maxTreeHeight = 5;
  public int treeWidth = 4;

  private PatternGenerator treePattern;
  private VegetationGenerator vegetation = new VegetationGenerator();

  @Override
  public void start() {
    createTreePattern();
  }

  /// Run only once
  @Override
  public int getBlockType(int x, int y, int z, int groundHeight, GenData data) {

    if (y <= groundHeight) {
      return -1;
    }

    int[] anchor = findTreeAnchor(x, z);
    if (anchor == null) {
      return -1;
    }

    int anchorX = anchor[0];
    int anchorZ = anchor[1];
    if (!vegetation.shouldPlaceTree(anchorX, anchorZ, groundHeight, data)) {
      return -1;
    }

    int th = data.getTreeHeight(anchorX, anchorZ, minTreeHeight, maxTreeHeight);
    int radius = data.getTreeRadius(anchorX, anchorZ, treeWidth / 2);
    int lh = radius + 2;

    Color pixel = treePattern.getInfinityPixel(x, z);
    if (y <= groundHeight + th) {
      float r = pixel.getFloatRed();
      if (r > 0) {
        return Blocks.LOG;
      }
    } else if (y >= groundHeight + th && y <= groundHeight + th + lh) {
      float g = pixel.getFloatGreen();
      int leafLevel = y - (groundHeight + th);
      int crownRadius = radius;
      if (leafLevel == 0 || leafLevel == lh) {
        crownRadius = java.lang.Math.max(1, radius - 1);
      }

      if (g > 0 && isInsideCrown(x, z, anchorX, anchorZ, crownRadius)) {
        return Blocks.LEAVES;
      } else {
        float r = pixel.getFloatRed();
        if (r > 0) {
          return Blocks.LEAVES;
        } 
      }
    }
    return -1;
  }

  int[] findTreeAnchor(int x, int z) {
    Color center = treePattern.getInfinityPixel(x, z);
    if (center.getFloatRed() > 0f) {
      return new int[] { x, z };
    }

    int searchRadius = java.lang.Math.max(1, treeWidth);
    int bestX = 0;
    int bestZ = 0;
    int bestDistance = 999999;
    for (int tx = x - searchRadius; tx <= x + searchRadius; tx++) {
      for (int tz = z - searchRadius; tz <= z + searchRadius; tz++) {
        Color pixel = treePattern.getInfinityPixel(tx, tz);
        if (pixel.getFloatRed() > 0f) {
          int dx = tx - x;
          int dz = tz - z;
          int distance = dx * dx + dz * dz;
          if (distance < bestDistance) {
            bestDistance = distance;
            bestX = tx;
            bestZ = tz;
          }
        }
      }
    }

    if (bestDistance < 999999) {
      return new int[] { bestX, bestZ };
    }
    return null;
  }

  boolean isInsideCrown(int x, int z, int anchorX, int anchorZ, int crownRadius) {
    int dx = x - anchorX;
    int dz = z - anchorZ;
    return dx * dx + dz * dz <= crownRadius * crownRadius;
  }

  void createTreePattern() {
    int r = 128;
    treePattern = new PatternGenerator(r);
    treePattern.setScale(5f);
    treePattern.setMargin(0.2f);
    treePattern.build();
    int leavesWidth = (int) java.lang.Math.ceil(treeWidth / 2f);

    for (int x = 0; x < r; x++) {
      for (int y = 0; y < r; y++) {
        Color pixel = treePattern.getDirectPixel(x, y);

        float v = pixel.getFloatRed();
        if (v <= 0) {

          squareLoop:
          {
            for (int x2 = x - leavesWidth; x2 < x + leavesWidth; x2++) {
              for (int y2 = y - leavesWidth; y2 < y + leavesWidth; y2++) {
                if (x2 > 0 && x2 < r) {
                  if (y2 > 0 && y2 < r) {
                    v = detectSideRed(treePattern, v, x2, y2);
                    if (v > 0) {
                      break squareLoop;
                    }
                  }
                }
              }
            }
          }

          if (v > 0) {
            pixel.setFloats(0, 1, 0);
            treePattern.setDirectPixel(x, y, pixel);
          }
        } else {
          pixel.setFloats(1, 0, 0);
          treePattern.setDirectPixel(x, y, pixel);
        }
      }
    }
    treePattern.getTexture().apply();
  }

  float detectSideRed(PatternGenerator p, float v, int x, int y) {
    if (v > 0) return v;
    Color c = p.getDirectPixel(x, y);
    if (c.getFloatRed() > 0f) {
      v = 1;
    }
    return v;
  }
}
