package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/**
 * @Author Lucas Leandro (ITsMagic Founder)
*/
public interface IFPSoundDic{ 
   FPSoundMaterialType getType();
   int soundCount();
   SoundFile soundAt(int index);
   float getSoundVolume();
}
