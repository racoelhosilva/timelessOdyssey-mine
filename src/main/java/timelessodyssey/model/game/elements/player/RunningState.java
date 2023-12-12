package timelessodyssey.model.game.elements.player;

import timelessodyssey.model.Vector;

public class RunningState extends PlayerState {
    public static final double MIN_VELOCITY = 1.7;

    public RunningState(Player player) {
        super(player);
    }

    @Override
    public Vector jump() {
        Vector newVelocity = new Vector(
                getPlayer().getVelocity().x(),
                getPlayer().getVelocity().y() - getPlayer().getJumpBoost()
        );
        return updateVelocity(newVelocity);
    }

    @Override
    public Vector dash() {
        return applyCollisions(new Vector(
                getPlayer().isFacingRight() ? getPlayer().getDashBoost() : -getPlayer().getDashBoost(),
                getPlayer().getVelocity().y()
        ));
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
        if (getPlayer().isOverMaxXVelocity())
            return new DashingState(getPlayer());
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