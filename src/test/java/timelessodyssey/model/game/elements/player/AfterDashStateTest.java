package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AfterDashStateTest {

    private Player player;

    private AfterDashState afterDashState;

    private Scene mockedScene;

    @BeforeEach
    void setup() {
        mockedScene = mock(Scene.class);
        player = new Player(0, 0, mockedScene);
        afterDashState = new AfterDashState(player);
        player.setState(afterDashState);
        player.setFacingRight(true);
        player.setVelocity(new Vector(1.8, 2.0));
        when(mockedScene.getFriction()).thenReturn(0.75);
        when(mockedScene.getGravity()).thenReturn(0.25);
    }

    @Test
    void jump() {
        Vector result = afterDashState.jump();

        assertEquals(1.35, result.x());
        assertEquals(2.25, result.y());
    }

    @Test
    void dash() {
        Vector result = afterDashState.dash();

        assertEquals(1.35, result.x());
        assertEquals(2.25, result.y());
        assertTrue(player.isFacingRight());
    }

    @Test
    void updateVelocity() {
        Vector result = afterDashState.updateVelocity(player.getVelocity());

        assertEquals(1.35, result.x());
        assertEquals(2.25, result.y());

    }

    @Test
    void getNextState_Dead() {
        when(mockedScene.isDying()).thenReturn(true);

        PlayerState nextState = afterDashState.getNextState();

        assertTrue(nextState instanceof DeadState);
    }

    @Test
    void getNextState_Idle() {
        when(player.isOnGround()).thenReturn(true);
        player.setVelocity(new Vector(0.0, 0.0));

        PlayerState nextState = afterDashState.getNextState();

        assertTrue(nextState instanceof IdleState);
    }

    @Test
    void getNextState_Walking() {
        when(player.isOnGround()).thenReturn(true);
        player.setVelocity(new Vector(1.0, 0.0));

        PlayerState nextState = afterDashState.getNextState();

        assertTrue(nextState instanceof WalkingState);
    }

    @Test
    void getNextState_Running() {
        when(player.isOnGround()).thenReturn(true);
        player.setVelocity(new Vector(2.0, 0.0));

        PlayerState nextState = afterDashState.getNextState();

        assertTrue(nextState instanceof RunningState);
    }
}