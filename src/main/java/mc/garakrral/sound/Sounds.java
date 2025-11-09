package mc.garakrral.sound;

import com.kaplanlib.api.builder.SoundBuilder;

import static mc.garakrral.sound.SoundType.GOBLIN_AMBIENT;
import static mc.garakrral.sound.SoundType.GOBLIN_DEATH;
import static mc.garakrral.sound.SoundType.GOBLIN_HURT;
import static mc.garakrral.sound.SoundType.MACHINE_RUN;
import static mc.garakrral.sound.SoundType.OPEN;
import static mc.garakrral.sound.SoundType.WALK_COPPER;
import static mc.garakrral.sound.SoundType.WALK_GRASS;
import static mc.garakrral.sound.SoundType.WALK_IRON;
import static mc.garakrral.sound.SoundType.GOBLIN_HORSE_SPLASH;

public class Sounds {
    public static void touch() {}

    static {
        GOBLIN_AMBIENT = SoundBuilder.buildSound("goblin.ambient");
        GOBLIN_HURT = SoundBuilder.buildSound("goblin.hurt");
        GOBLIN_DEATH = SoundBuilder.buildSound("goblin.death");
        GOBLIN_HORSE_SPLASH = SoundBuilder.buildSound("goblin_horse.splash");
        WALK_GRASS = SoundBuilder.buildSound("walk.grass");
        WALK_COPPER = SoundBuilder.buildSound("walk.copper");
        WALK_IRON = SoundBuilder.buildSound("walk.iron");
        MACHINE_RUN = SoundBuilder.buildSound("machine.run");
        OPEN = SoundBuilder.buildSound("general.open");
    }
}
