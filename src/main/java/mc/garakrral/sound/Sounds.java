package mc.garakrral.sound;

import com.kaplanlib.api.builder.SoundBuilder;

import static mc.garakrral.sound.SoundType.GOBLIN_AMBIENT;
import static mc.garakrral.sound.SoundType.GOBLIN_HURT;
import static mc.garakrral.sound.SoundType.GOBLIN_DEATH;

public class Sounds {
    public static void touch() {}
    static {
        GOBLIN_AMBIENT = SoundBuilder.buildSound("goblin.ambient");
        GOBLIN_HURT = SoundBuilder.buildSound("goblin.hurt");
        GOBLIN_DEATH = SoundBuilder.buildSound("goblin.death");
    }

}
