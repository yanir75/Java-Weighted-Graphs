package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationTest {

    @Test
    void x_y_z() {
        GeoLocation g = new Location(1,2,0);
        assertTrue(g.x()==1);
        assertTrue(g.y()==2);
        assertTrue(g.z()==0);
    }


    @Test
    void distance() {
        GeoLocation g = new Location(3,4,0);
        GeoLocation g1 = new Location(0,0,0);
        assertTrue(g1.distance(g)==5);
    }

    @Test
    void testToString() {
        GeoLocation g1 = new Location(0,0,0);
        assertEquals(g1.toString(),"(0.0, 0.0, 0.0)");
    }
}