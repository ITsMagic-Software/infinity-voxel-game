package JAVARuntime;

// Useful imports
import java.util.*;

/** @Author Lucas Leandro (ITsMagic Founder) */
public class FPPlayerCB extends Component implements IFPPlayer {

  @Override
  public String getComponentMenu() {
    return "AFPP";
  }

  @Order(idx = {-8})
  public float walkSpeed = 2;

  @Order(idx = {-7})
  public float runSpeed = 4;

  @Order(idx = {-6})
  public float moveLerpSpeed = 2;

  @Order(idx = {-5})
  public float jumpSpeed = 5;

  @Order(idx = {-4})
  public float horizontalSensitivity = 10f;

  @Order(idx = {-3})
  public float verticalSensitivity = 6f;

  @Order(idx = {-2})
  public float cameraMinAngle = -89;

  @Order(idx = {-1})
  public float cameraMaxAngle = 89;

  private Characterbody cb;
  private AFPPCamera ac;
  private IFPController ct;
  private IFPStepsEmitter se;
  private float appliedSpeed = 0;
  private Vector2 zero = new Vector2();
  private boolean walking;
  private Vector2 walkVector = new Vector2();
  private Set<Component> waterTriggers = new HashSet();

  /// Run only once
  @Override
  public void start() {}

  /// Repeat every frame
  @Override
  public void repeat() {
    getCT();
    if (ct == null) {
      Terminal.log("AFPP Needs a IFPController component...");
      return;
    }
    getCB();
    getSE();

    move();
    rotate();
    emitSteps();
  }

  private void move() {
    Vector2 joy = ct.getWalkDir();
    if (joy == null) joy = zero;
    float speed = (ct.wantRun()) ? runSpeed : walkSpeed;
    appliedSpeed = Math.lerpInSeconds(appliedSpeed, speed, moveLerpSpeed * 50f);
    cb.setForwardSpeed(appliedSpeed * joy.y);
    cb.setSideSpeed(appliedSpeed * -joy.x);
    cb.setJumpSpeed(jumpSpeed);

    walking = (joy.x != 0 || joy.y != 0);
    walkVector.set(joy);
    walkVector.mulLocal(appliedSpeed);

    if (ct.wantJump()) {
      if (cb.canJump()) {
        cb.jump();

        if (se != null) {
          se.prepareImmediate();
        }
      }
    }
  }

  private void emitSteps() {
    if (walking) {
      getSE();
      if (se != null) {
        if (cb.isGrounded()) {
          se.onWalk(walkVector.length(), cb, this);
        }
      }
    }
  }

  private void rotate() {
    Vector2 slide = ct.getSlideAxis();
    if (slide == null) slide = zero;

    /// horizontal slide
    myObject.getTransform().rotateInSeconds(0, -slide.getX() * horizontalSensitivity * 360 * 0.0041f, 0);

    /// vertical slide
    getAC();
    if (ac == null) {
      Terminal.log("AFPPCamera was not found...");
      return;
    }
    ac.minAngle = cameraMinAngle;
    ac.maxAngle = cameraMaxAngle;
    ac.currentAngle += -slide.getY() * verticalSensitivity * 90 * 0.0008f;
    ac.set();
  }

  private AFPPCamera getAC() {
    if (ac == null) {
      ac = myObject.findComponentInChildren(AFPPCamera.class);
    }
    return ac;
  }

  private Characterbody getCB() {
    if (cb == null) {
      cb = myObject.findComponent(Characterbody.class);
      if (cb == null) {
        print(this.getClass().getSimpleName() + " needs to be attached on a Characterbody object");
      } 
    }
    return cb;
  }

  private IFPController getCT() {
    if (ct == null) {
      for (int x = 0; x < myObject.componentCount(); x++) {
        Component c = myObject.getComponentAt(x);
        if (c instanceof IFPController) {
          ct = (IFPController) c;
          break;
        }
      }
    }
    return ct;
  }

  private IFPStepsEmitter getSE() {
    if (se == null) {
      for (int x = 0; x < myObject.componentCount(); x++) {
        Component c = myObject.getComponentAt(x);
        if (c instanceof IFPStepsEmitter) {
          se = (IFPStepsEmitter) c;
          break;
        }
      }
    }
    return se;
  }

  /* Override */
  public void addWaterTrigger(Component comp) {
    waterTriggers.add(comp);
  }

  /* Override */
  public void removeWaterTrigger(Component comp) {
    waterTriggers.remove(comp);
  }

  /* Override */
  public boolean isOnWater() {
    return !waterTriggers.isEmpty();
  }
}