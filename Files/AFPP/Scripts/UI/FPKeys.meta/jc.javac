package JAVARuntime;

// Useful imports
import java.util.*;

/**
 * @Author Lucas Leandro (ITsMagic Founder)
*/
public class FPKeys extends Component implements IFPController {

  @Override
  public String getComponentMenu() {
    return "AFPP";
  }

  public String walkJoystickAxisName = "joystick";
  public String slideAxisName = "slide";
  public String jumpButtonKey = "jump";
  public String runButtonKey = "run";

  private final KeyHandler runKey = new KeyHandler("");
  private final KeyHandler jumpKey = new KeyHandler("");
  private final AxisHandler walkJoy = new AxisHandler("");
  private final AxisHandler slideAxis = new AxisHandler("");

  @Override
  public void repeat() {
    runKey.update(runButtonKey);
    jumpKey.update(jumpButtonKey);
    walkJoy.update(walkJoystickAxisName);
    slideAxis.update(slideAxisName);
  } 

  /* Override */
  public Vector2 getWalkDir() {
    Axis a = walkJoy.get();
    if (a == null) return null;

    return a.value;
  }

  /* Override */
  public Vector2 getSlideAxis() {
    Axis a = slideAxis.get();
    if (a == null) return null;

    return a.value;
  }

  /* Override */
  public boolean wantJump() {
    Key k = jumpKey.get();
    if (k == null) return false;
    return k.down || k.pressed;
  }

  /* Override */
  public boolean wantRun() {
    Key k = runKey.get();
    if (k == null) return false;
    return k.down || k.pressed;
  }

  private class KeyHandler {
    Key key;
    String name;

    public KeyHandler(String name) {
      this.name = name;
    }

    public void update(String name) {
      if (this.name == name) {
        return;
      }

      this.name = name;
      this.key = null;
    }

    public Key get() {
      if (key == null) key = Input.getKey(name);

      return key;
    }
  }

  private class AxisHandler {
    Axis axis;
    String name;

    public AxisHandler(String name) {
      this.name = name;
    }

    public void update(String name) {
      if (this.name == name) {
        return;
      }

      this.name = name;
      this.axis = null;
    }

    public Axis get() {
      if (axis == null) axis = Input.getAxis(name);

      return axis;
    }
  }
}
