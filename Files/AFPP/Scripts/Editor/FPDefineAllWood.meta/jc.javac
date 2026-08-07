package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author Lucas Leandro (ITsMagic Founder) */
public class FPDefineAllWood extends ObjectsPanelMenu {

  public FPDefineAllWood() {
    super("SoundMaterial/Define all: Wood");
  }

  @Override
  public void onClick(final SpatialObject o) {
    Thread.runOnEngine(
        new Runnable() {
          public void run() {
            FPEditorUtils.defineSoundMaterial(o, FPSoundMaterialType.WOOD, true);
          }
        });
  }
} 
