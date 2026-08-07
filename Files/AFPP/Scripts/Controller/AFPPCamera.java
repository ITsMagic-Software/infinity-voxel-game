package JAVARuntime;

// Useful imports
import java.util.*;

/**
 * @Author Lucas Leandro (ITsMagic Founder)
*/
public class AFPPCamera extends Component {

  @Override
  public String getComponentMenu() {
    return "AFPP";
  }

  public float offset = 0;
  public float currentAngle;
  
  @Hide 
  public float minAngle = -80;
  @Hide 
  public float maxAngle = 80;

  private final Vector3 dir = new Vector3();

  public void set() {
    currentAngle = Math.clamp(minAngle, currentAngle, maxAngle);
    float cos = Math.cos(currentAngle + offset);
    float sin = Math.sin(currentAngle + offset);
    dir.set(0, sin, cos);
    myObject.rotation.localLookTo(dir);
  } 
}
