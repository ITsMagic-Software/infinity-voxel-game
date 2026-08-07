package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author */
public class BiomeGenerator extends Generator {

  public static final int PLAINS = 0;
  public static final int FOREST = 1;
  public static final int DESERT = 2;
  public static final int BEACH = 3;
  public static final int MOUNTAINS = 4;

  public int getBiome(float moisture, float temperature, int groundHeight, int beachLevel) {
    if (groundHeight <= beachLevel) {
      return BEACH;
    }
    if (groundHeight >= beachLevel + 24) {
      return MOUNTAINS;
    }
    if (temperature > 0.62f && moisture < 0.38f) {
      return DESERT;
    }
    if (moisture > 0.52f) {
      return FOREST;
    }
    return PLAINS;
  }
}
