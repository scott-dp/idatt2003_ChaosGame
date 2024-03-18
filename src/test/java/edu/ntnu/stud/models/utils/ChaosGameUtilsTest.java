package edu.ntnu.stud.models.utils;

import edu.ntnu.stud.models.utils.ChaosGameUtils;
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
  }

  @Nested
  class NegativeTests {
    @Test
    void testValidatePositiveInteger() {
      assertThrows(IllegalArgumentException.class, () -> ChaosGameUtils.validatePositiveInteger(0));
    }
  }

}
