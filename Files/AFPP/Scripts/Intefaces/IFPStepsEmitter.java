package JAVARuntime;

// Useful imports
import java.util.*;

/** @Author Lucas Leandro (ITsMagic Founder) */
public interface IFPStepsEmitter {
    void onWalk(float speed, PhysicsComponent physicsEntity, IFPPlayer player);
    void addSoundDic(IFPSoundDic dic);
    void prepareImmediate();
} 