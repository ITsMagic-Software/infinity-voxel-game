package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author */
public class FPSoundMaterialDictionary extends Component implements IFPSoundDic {
       
  @Override
  public String getComponentMenu() {
    return "AFPP";
  } 
  
  @Order(idx = {1})
  public FPSoundMaterialType type = FPSoundMaterialType.CONCRETE;
  
  @Order(idx = {2})
  public float volume = 0.5f;

  @Order(idx = {2})
  public List<SoundFile> sounds = new ArrayList();
  
  /* Overridde */
  public FPSoundMaterialType getType(){
      return type;
  }
  
  /* Override */
  public int soundCount(){
      return sounds.size();
  }
  
  /* Override */
  public SoundFile soundAt(int index){
      return sounds.get(index);
  }  
  
  /* Override */
  public float getSoundVolume(){
      return volume;
  }
  
  @Override
  public void start(){
     findEmitterInObject(myObject);
     
     // Spawn sounds for cache
     SpatialObject cacheParent = new SpatialObject(type.toString() + " cache");
     cacheParent.parent = myObject;
     
     for(int x = 0; x < sounds.size();x ++){
         SoundFile f = sounds.get(x);
         
         SpatialObject o = new SpatialObject("["+x+"]");
         
         SoundPlayer p = new SoundPlayer();
         o.addComponent(p);
         
         p.setSoundFile(f);
         p.volume = 0f;         
                  
         o.parent = cacheParent;
         o.setStatic(true);
     }
  }
  
  void findEmitterInObject(SpatialObject o){
      for (int x = 0; x < o.componentCount(); x++) {
        Component c = o.getComponentAt(x);
        if (c instanceof IFPStepsEmitter) {
          IFPStepsEmitter se = (IFPStepsEmitter) c;
          se.addSoundDic(this);
          return;
        }
      }
      if(o.parent != null){
          findEmitterInObject(o.parent);
      }
  }
} 
