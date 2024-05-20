package edu.ntnu.stud.models.chaosgamehandling;

import edu.ntnu.stud.models.mathematics.Complex;
import edu.ntnu.stud.models.mathematics.Vector2D;
import edu.ntnu.stud.models.transform.JuliaTransform;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ChaosGameDescriptionFactoryTest {
  @Test
  void testCreateSierpinskiDescription() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createSierpinskiDescription();
    assertEquals(description.getMinCoords(), new Vector2D(0, 0));
    assertEquals(description.getMaxCoords(), new Vector2D(1, 1));
    assertEquals(description.getTransforms().size(), 3);
  }

  @Test
  void testCreateLevyCurveDescription() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createLevyCurveDescription();
    assertEquals(description.getMinCoords(), new Vector2D(-1.53, -1.53));
    assertEquals(description.getMaxCoords(), new Vector2D(0.53, 0.53));
    assertEquals(description.getTransforms().size(), 2);
  }

  @Test
  void testCreateBarnsleyFernDescription() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.createBarnsleyDescription();
    assertEquals(description.getMinCoords(), new Vector2D(-2.1820, 0));
    assertEquals(description.getMaxCoords(), new Vector2D(2.6558, 9.9983));
    assertEquals(description.getTransforms().size(), 4);
  }

  @Test
  void testCreateJuliaDescription1() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.getJuliaSetDescription1();
    assertEquals(description.getMinCoords(), new Vector2D(-1.6, -1));
    assertEquals(description.getMaxCoords(), new Vector2D(1.6, 1));
    JuliaTransform transform = (JuliaTransform) description.getTransforms().get(0);
    assertEquals(transform.getConstantPoint(), new Complex(-.74543, .11301));
  }

  @Test
  void testCreateJuliaDescription2() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.getJuliaSetDescription2();
    assertEquals(description.getMinCoords(), new Vector2D(-1.6, -1));
    assertEquals(description.getMaxCoords(), new Vector2D(1.6, 1));
    JuliaTransform transform = (JuliaTransform) description.getTransforms().get(0);
    assertEquals(transform.getConstantPoint(), new Complex(-1.234, .11301));
  }

  @Test
  void testCreateJuliaDescription3() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.getJuliaSetDescription3();
    assertEquals(description.getMinCoords(), new Vector2D(-1.6, -1));
    assertEquals(description.getMaxCoords(), new Vector2D(1.6, 1));
    JuliaTransform transform = (JuliaTransform) description.getTransforms().get(0);
    assertEquals(transform.getConstantPoint(), new Complex(-.1234, .81301));
  }

  @Test
  void testCreateJuliaDescription4() {
    ChaosGameDescription description = ChaosGameDescriptionFactory.getJuliaSetDescription4();
    assertEquals(description.getMinCoords(), new Vector2D(-1.6, -1));
    assertEquals(description.getMaxCoords(), new Vector2D(1.6, 1));
    JuliaTransform transform = (JuliaTransform) description.getTransforms().get(0);
    assertEquals(transform.getConstantPoint(), new Complex(-.2234, 1.11301));
  }

}
