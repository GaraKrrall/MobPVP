package mc.garakrral.xpjump;

public interface XpJump {

    void setJumpStrength(int strength);

    boolean canJump();

    void startJumping(int height);

    void stopJumping();

    default int getJumpCooldown() {
        return 0;
    }
}
