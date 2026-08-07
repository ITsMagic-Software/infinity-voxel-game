package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author */
public class PatternGenerator {

  private Texture texture;
  private int resolution;
  private float scale;
  private float margin = 0.1f;

  private float offsetX = 0;
  private float offsetY = 0;

  public PatternGenerator(int resolution) {
    setResolution(resolution);
  }

  public PatternGenerator(int resolution, float scale, float margin) {
    setResolution(resolution);
    setMargin(margin);
    setScale(scale);
  }

  public void setResolution(int r) {
    if (r <= 0) throw new RuntimeException("Invalid resolution");
    this.resolution = r;
  }

  public void setScale(float s) {
    if (s <= 0) throw new RuntimeException("Invalid scale");
    this.scale = s;
  }

  public void setMargin(float m) {
    if (m <= 0 || m > 1) throw new RuntimeException("Invalid margin");
    this.margin = m;
  }

  public void setOffsets(float x, float y) {
    this.offsetX = x;
    this.offsetY = y;
  }

  public void build() {
    PerlinNoise pn = new PerlinNoise(scale);

    texture = new Texture(resolution, resolution);
    Color color = new Color();
    color.setFloats(0, 0, 0);
    for (int x = 0; x < resolution; x++) {
      for (int y = 0; y < resolution; y++) {
        texture.setPixel(x, y, color);
      }
    }
    texture.apply();

    for (int x = 0; x < resolution; x++) {
      for (int y = 0; y < resolution; y++) {
        float v = pn.noise(offsetX + x, offsetY + y);
        if (v < -1 + margin || v > 1 - margin) {
          v = 1f;
        } else {
          v = 0f;
        }
        v = fixSideColor(v, x - 1, y - 1);
        v = fixSideColor(v, x, y - 1);
        v = fixSideColor(v, x - 1, y);
        v = fixSideColor(v, x, y);
        v = fixSideColor(v, x + 1, y + 1);
        v = fixSideColor(v, x, y + 1);
        v = fixSideColor(v, x + 1, y);
        v = fixSideColor(v, x + 1, y - 1);
        v = fixSideColor(v, x - 1, y + 1);
        

        color.setFloats(v, v, v);
        texture.setPixel(x, y, color);
      }
    }
    texture.apply();
  }

  float fixSideColor(float v, int x, int y) {
    if (v <= 0) return v;
    Color c = getDirectPixel(x, y);
    if (c.getFloatRed() > 0f) {
      v = 0;
    }
    return v;
  }

  public Color getDirectPixel(int x, int y) {
    int lx = x;
    lx = (int) Math.clamp(0, x, resolution - 1);
    int ly = y;
    ly = (int) Math.clamp(0, y, resolution - 1);
    return texture.getPixel(lx, ly);
  }

  public void setDirectPixel(int x, int y, Color color) {
    int lx = x;
    lx = (int) Math.clamp(0, x, resolution - 1);
    int ly = y;
    ly = (int) Math.clamp(0, y, resolution - 1);
    texture.setPixel(lx, ly, color);
  }

  public Texture getTexture() {
    return texture;
  }

  public Color getInfinityPixel(int x, int y) {
    int r = resolution;
    if (x > r) {
      float p = (float) x / (float) r;
      int ip = (int) p;
      p = p - ip;
      x = (int) (p * r);
    }
    if (y > r) {
      float p = (float) y / (float) r;
      int ip = (int) p;
      p = p - ip;
      y = (int) (p * r);
    }
    {
      if (x < 0) {

        float p = (float) x / (float) r;
        p = -p;
        int ip = (int) p;
        p = p - ip;
        p = 1f - p;
        x = (int) (p * r);
      }
      if (y < 0) {
        float p = (float) y / (float) r;
        p = -p;
        int ip = (int) p;
        p = p - ip;
        p = 1f - p;
        y = (int) (p * r);
      }
    }
    return getDirectPixel(x, y);
  }

  public float getInfinityRed(int x, int y) {
    return getInfinityPixel(x, y).getFloatRed();
  }

  public float getInfinityGreen(int x, int y) {
    return getInfinityPixel(x, y).getFloatGreen();
  } 

  public float getInfinityBlue(int x, int y) {
    return getInfinityPixel(x, y).getFloatBlue();
  }
}