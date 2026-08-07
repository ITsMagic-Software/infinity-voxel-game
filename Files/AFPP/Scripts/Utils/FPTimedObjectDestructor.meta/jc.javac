package JAVARuntime;

// Useful imports
import java.util.*;
import java.text.*;
import java.net.*;
import java.math.*;
import java.io.*;
import java.nio.*;

/** @Author */
public class FPTimedObjectDestructor extends Component {

  @Override
  public String getComponentMenu() {
    return "AFPP";
  } 

  public float lifeTime = 3;
  private float timer = 0;

  /// Repeat every frame
  @Override
  public void repeat() {
    timer += Math.bySecond();
    if (timer >= lifeTime) {
      myObject.destroy();
    }
  }
}
