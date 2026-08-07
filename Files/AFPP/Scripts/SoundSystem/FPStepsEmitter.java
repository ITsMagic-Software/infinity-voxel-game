package JAVARuntime;

// Useful imports
import java.util.*;

/** @Author Lucas Leandro (ITsMagic Founder) */
public class FPStepsEmitter extends Component implements IFPStepsEmitter {

  @Override
  public String getComponentMenu() {
    return "AFPP";
  }

  @Order(idx = {1})
  public ObjectFile playerObject;

  @Order(idx = {3})
  public float stepDelay = 0.5f;

  @Order(idx = {4})
  public float baseFrequency = 0.5f;

  @Order(idx = {4})
  public float frequencyMultiplier = 0.2f;

  @Order(idx = {5})
  public float basePitch = 0.9f;

  @Order(idx = {6})
  public float pitchMultiplier = 0.05f;

  @Order(idx = {7})
  public float baseVolume = 0.6f;

  @Order(idx = {8})
  public float volumeMultiplier = 0.1f;

  private List<IFPSoundDic> sounds = new ArrayList();
  private float timer = 0;

  /* Override */
  public void onWalk(float speed, PhysicsComponent physicsEntity, IFPPlayer player) {
    timer += Math.bySecond() * (baseFrequency + (frequencyMultiplier * speed));
    if (timer >= stepDelay) {
      timer = 0;

      FPSoundMaterialType groundType = null;
      if (!player.isOnWater()) {
        groundType = determineGroundType(physicsEntity);
      } else {
        groundType = FPSoundMaterialType.WATER;
      } 

      IFPSoundDic mat = null;
      if (groundType == null) {
        if (!sounds.isEmpty()) {
          mat = sounds.get(0);
        }
      } else {
        for (int x = 0; x < sounds.size(); x++) {
          IFPSoundDic d = sounds.get(x);
          if (d.getType() == groundType) {
            mat = d;
            break;
          }
        }
      }

      if (mat != null) {
        SoundFile file = mat.soundAt(Random.range(0, mat.soundCount() - 1));

        SpatialObject o = myObject.instantiate(playerObject);
        SoundPlayer p = o.findComponent(SoundPlayer.class);

        p.setSoundFile(file);
        p.pitch = basePitch + (speed * pitchMultiplier);
        p.volume = (baseVolume + (speed * volumeMultiplier)) * mat.getSoundVolume();
      }
    }
  }

  private FPSoundMaterialType determineGroundType(PhysicsComponent physicsEntity) {
    FPSoundMaterialType groundType = null;

    Vector3 bcp = null;
    SpatialObject obj = null;
    for (int x = 0; x < physicsEntity.getCollisionsCount(); x++) {
      Collision col = physicsEntity.getCollisionAt(x);
      if(col.collider == null){
          continue;
      }

      if (bcp == null || bcp.y > col.contactPoint.y) {
        bcp = col.contactPoint;
        obj = col.collider.getObject();
      }

      for (int y = 0; y < col.contactCount(); y++) {
        Collision.Contact con = col.contactAt(y);

        if (bcp == null || bcp.y > con.contactPoint.y) {
          bcp = con.contactPoint;
          obj = con.collider.getObject();
        }
      }
    }

    if (obj != null) {
      FPSoundMaterial sm = obj.findComponent(FPSoundMaterial.class);
      if (sm != null) {
        groundType = sm.type;
      }
    }

    return groundType;
  }

  /* Override */
  public void addSoundDic(IFPSoundDic dic) {
    sounds.add(dic);
  }

  /* Override */
  public void prepareImmediate() {
    timer = stepDelay;
  }
}