package it.unibo.boundaryWalk;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BuondaryWalkTest {
    private final BoundaryWalk app = new BoundaryWalk();

    @Before
    public void putRobotInStartPosition(){
        app.resetRobot();
    }

    @Test
    public void testRobotRunPerimeter(){
        assertTrue(app.startWithCornerDetection() == 4);
    }
}
