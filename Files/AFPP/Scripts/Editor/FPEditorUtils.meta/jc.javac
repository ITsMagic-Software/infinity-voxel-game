package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author */
public class FPEditorUtils {

  public static void defineSoundMaterial(
      SpatialObject o, FPSoundMaterialType type, boolean children) {
    FPSoundMaterial sm = o.findComponent(FPSoundMaterial.class);
    if (sm != null) {
      sm.type = type;
    } else {
      if (o.findComponent(Collider.class) != null) {
        sm = new FPSoundMaterial();
        sm.type = type;
        o.addComponent(sm);
      } 
    }

    if (children) {
      for (int x = 0; x < o.getChildCount(); x++) {
        defineSoundMaterial(o.getChildAt(x), type, children);
      }
    }
  }
}
