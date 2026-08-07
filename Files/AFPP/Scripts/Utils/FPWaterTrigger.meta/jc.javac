package JAVARuntime;

// Useful imports
import java.util.*;

/** @Author Lucas Leandro (ITsMagic Founder) */
public class FPWaterTrigger extends Component {

  @Override
  public String getComponentMenu() {
    return "AFPP";
  }

  @AutoWired private AreaTrigger at;
  private Map<SpatialObject, IFPPlayer> players = new HashMap();
  private Set<SpatialObject> onObjects = new HashSet();

  /// Run only once
  @Override
  public void start() {}

  /// Repeat every frame
  @Override
  public void repeat() {
    int collisionCount = at.getCollisionsCount();
    if (collisionCount <= 0) {
      if (!onObjects.isEmpty()) {
        for (SpatialObject o : onObjects) {
          IFPPlayer player = players.get(o);
          if (player != null) {
            player.removeWaterTrigger(this);
          }
          players.remove(o);
        } 
        onObjects.clear();
      }
      return;
    }

    for (int x = 0; x < collisionCount; x++) {
      Collision col = at.getCollisionAt(x);

      if (col.object != null) {
        SpatialObject o = col.object;

        IFPPlayer player = null;
        for (int y = 0; y < o.componentCount(); y++) {
          Component c = o.getComponentAt(y);
          if (c instanceof IFPPlayer) {
            player = (IFPPlayer) c;
            break;
          }
        }
        if (player != null) {
          player.addWaterTrigger(this);
          onObjects.add(o);
          players.put(o, player);
        } else {
          IFPPlayer p = players.get(o);
          if (p != null) {
            p.removeWaterTrigger(this);
            players.remove(o);
            onObjects.remove(o);
          }
        }
      }
    }
  }
}