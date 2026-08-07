package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author */
public class VUtils {

  public static void debugPattern(PatternGenerator p) {
    SpatialObject o = new SpatialObject("Pattern debug");
    Material material = new Material();
    material.setTexture("Texture", p.getTexture());
    ModelRenderer mr = new ModelRenderer();
    mr.vertex = Vertex.loadPrimitive(Vertex.CUBE);
    mr.material = material;
    o.addComponent(mr);
  }
}
