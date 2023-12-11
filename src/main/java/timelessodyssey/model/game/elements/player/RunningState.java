package timelessodyssey.model.game.elements.player;

import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

public class RunningState extends PlayerState {
    public static final double MIN_VELOCITY = 1.7;

    public RunningState(Player player) {
        super(player);
    }

    @Override
    public Vector updateVelocity(Vector velocity) {
        Vector newVelocity = new Vector(
                velocity.x() * getPlayer().getScene().getFriction(),
                velocity.y()
        );
        return limitVelocity(applyCollisions(newVelocity));
    }

    @Override
    public PlayerState getNextState() {
        if (!getPlayer().isOnGround()) {
            if (getPlayer().getVelocity().y() < 0)
                return new JumpingState(getPlayer());
            return new FallingState(getPlayer());
        }
        if (Math.abs(getPlayer().getVelocity().x()) < RunningState.MIN_VELOCITY)
            return new IdleState(getPlayer());
        return this;
    }
}
