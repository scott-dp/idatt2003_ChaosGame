package edu.ntnu.stud.models.utils;

import edu.ntnu.stud.models.mathematics.Vector2D;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChaosGameUtilsTest {

  @Nested
  class PositiveTests {

    @Test
    void testAreDoublesEqual() {
      assertTrue(ChaosGameUtils.areDoublesEqual(1.001, 1.001));
    }

    @Test
    void testRoundDoubleToSetDecimals() {
      assertEquals(ChaosGameUtils.roundDoubleToSetDecimals(2.817382, 3), 2.817);
      assertEquals(ChaosGameUtils.roundDoubleToSetDecimals(5.8899, 3), 5.890);
    }

    @Test
    void testValidatePositiveInteger() {
      assertEquals(ChaosGameUtils.validatePositiveInteger(1), 1);
    }

    @Test
    void testValidateMinAndMaxCoords() {
      assertDoesNotThrow(() -> ChaosGameUtils.validateMinAndMaxCoords(new Vector2D(1, 1), new Vector2D(2, 2)));
    }

    @Test
    void testValidateSignField() {
      assertEquals(ChaosGameUtils.validateSignField(1), 1);
      assertEquals(ChaosGameUtils.validateSignField(-1), -1);
    }

    @Test
    void testVerifyPointBetweenMinAndMax() {
      assertDoesNotThrow(()-> ChaosGameUtils.verifyPointBetweenMinAndMax(new Vector2D(1, 1), new Vector2D(0, 0), new Vector2D(2, 2)));
      assertDoesNotThrow(()-> ChaosGameUtils.verifyPointBetweenMinAndMax(new Vector2D(0, 1), new Vector2D(0, 0), new Vector2D(2, 2)));
      assertDoesNotThrow(()-> ChaosGameUtils.verifyPointBetweenMinAndMax(new Vector2D(2, 1), new Vector2D(0, 0), new Vector2D(2, 2)));
    }
  }

  @Nested
  class NegativeTests {
    @Test
    void testValidatePositiveInteger() {
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validatePositiveInteger(0));
    }

    @Test
    void testAreDoublesNotEqual() {
      assertFalse(ChaosGameUtils.areDoublesEqual(1.0001, 1));
    }

    @Test
    void testValidateMinAndMaxCoords() {
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validateMinAndMaxCoords(new Vector2D(1, 1), new Vector2D(1, 0)));
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validateMinAndMaxCoords(new Vector2D(1, 1), new Vector2D(0, 0)));
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validateMinAndMaxCoords(new Vector2D(1, 1), new Vector2D(0, 1)));
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validateMinAndMaxCoords(new Vector2D(1, 2), new Vector2D(2, 2)));
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validateMinAndMaxCoords(new Vector2D(2, 2), new Vector2D(2, 2)));
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validateMinAndMaxCoords(new Vector2D(2, 1), new Vector2D(2, 2)));
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validateMinAndMaxCoords(new Vector2D(1, 3), new Vector2D(2, 2)));
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validateMinAndMaxCoords(new Vector2D(3, 1), new Vector2D(2, 2)));
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validateMinAndMaxCoords(new Vector2D(3, 3), new Vector2D(2, 2)));
    }

    @Test
    void testValidateSignField() {
      assertThrows(IllegalArgumentException.class, ()-> ChaosGameUtils.validateSignField(0));
      assertThrows(IllegalArgumentException.class, ()-> ChaosGameUtils.validateSignField(-2));
      assertThrows(IllegalArgumentException.class, ()-> ChaosGameUtils.validateSignField(2));
    }

    @Test
    void testVerifyPointBetweenMinAndMax() {
      assertThrows(IllegalArgumentException.class, ()-> ChaosGameUtils.verifyPointBetweenMinAndMax(new Vector2D(0, 0), new Vector2D(1, 1), new Vector2D(2,2)));
      assertThrows(IllegalArgumentException.class, ()-> ChaosGameUtils.verifyPointBetweenMinAndMax(new Vector2D(1, 3), new Vector2D(1, 1), new Vector2D(2,2)));
      assertThrows(IllegalArgumentException.class, ()-> ChaosGameUtils.verifyPointBetweenMinAndMax(new Vector2D(0, 2), new Vector2D(1, 1), new Vector2D(2,2)));
      assertThrows(IllegalArgumentException.class, ()-> ChaosGameUtils.verifyPointBetweenMinAndMax(new Vector2D(3, 3), new Vector2D(1, 1), new Vector2D(2,2)));
      assertThrows(IllegalArgumentException.class, ()-> ChaosGameUtils.verifyPointBetweenMinAndMax(new Vector2D(3, 1), new Vector2D(1, 1), new Vector2D(2,2)));
      assertThrows(IllegalArgumentException.class, ()-> ChaosGameUtils.verifyPointBetweenMinAndMax(new Vector2D(2, 0), new Vector2D(1, 1), new Vector2D(2,2)));
    }
  }

}
