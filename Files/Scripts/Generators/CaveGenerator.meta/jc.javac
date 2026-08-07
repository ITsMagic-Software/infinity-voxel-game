package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author */
public class CaveGenerator extends Generator {

  @Override
  public int getBlockType(int x, int y, int z, int groundHeight, GenData data) {
    if (data.shouldCarveCave(x, y, z, groundHeight)) {
      return Blocks.AIR;
    }
    return -1;
  }
}
