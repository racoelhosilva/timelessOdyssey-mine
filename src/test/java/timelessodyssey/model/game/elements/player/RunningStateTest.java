package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RunningStateTest {

    private Player player;

    private RunningState runningState;

    private Scene mockedScene;

    @BeforeEach
    void setup() {
        mockedScene = mock(Scene.class);
        player = new Player(0, 0, mockedScene);
        runningState = new RunningState(player);
        player.setState(runningState);
        player.setFacingRight(true);
        player.setVelocity(new Vector(1.8, 0));
        when(mockedScene.getFriction()).thenReturn(0.75);
    }

    @Test
    void jump() {
        Vector result = runningState.jump();

        assertEquals(1.35, result.x());
        assertEquals(-3.6, result.y());
    }

    @Test
    void dash() {
        Vector result = runningState.dash();

        assertEquals(5.0, result.x());
        assertEquals(0.0, result.y());
        assertTrue(player.isFacingRight());
    }

    @Test
    void updateVelocity() {
        Vector result = runningState.updateVelocity(player.getVelocity());

        assertEquals(1.35, result.x());
        assertEquals(0.0, result.y());

    }

    @Test
    void getNextState_Dead() {
        when(mockedScene.isDying()).thenReturn(true);

        PlayerState nextState = runningState.getNextState();

        assertTrue(nextState instanceof DeadState);
    }

    @Test
    void getNextState_Dashing() {
        player.setVelocity(new Vector(10, player.getVelocity().y()));

        PlayerState nextState = runningState.getNextState();

        assertTrue(nextState instanceof DashingState);
    }

    @Test
    void getNextState_Jumping() {
        when(player.isOnGround()).thenReturn(false);
        player.setVelocity(new Vector(player.getVelocity().x(), -10));

        PlayerState nextState = runningState.getNextState();

        assertTrue(nextState instanceof JumpingState);
    }

    @Test
    void getNextState_Falling() {
        when(player.isOnGround()).thenReturn(false);
        player.setVelocity(new Vector(player.getVelocity().x(), 10));

        PlayerState nextState = runningState.getNextState();

        assertTrue(nextState instanceof FallingState);
    }

    @Test
    void getNextState_Idle() {
        when(player.isOnGround()).thenReturn(true);
        player.setVelocity(new Vector(1.5, 0));

        PlayerState nextState = runningState.getNextState();

        assertTrue(nextState instanceof IdleState);
    }
}