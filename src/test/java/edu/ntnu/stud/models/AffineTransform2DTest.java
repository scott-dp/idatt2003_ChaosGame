package edu.ntnu.stud.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AffineTransform2DTest {

    private AffineTransform2D identityTransform;
    private AffineTransform2D translationTransform;
    private AffineTransform2D rotationTransform;

    @BeforeEach
    public void setUp() {
        // Identity transformation (no change)
        identityTransform = new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2D(0, 0));

        // Translation transformation (move by a certain vector)
        translationTransform = new AffineTransform2D(new Matrix2x2(1, 0, 0, 1), new Vector2D(5, 5));

        // Rotation transformation (90 degrees clockwise without translation)
        rotationTransform = new AffineTransform2D(new Matrix2x2(0, -1, 1, 0), new Vector2D(0, 0));
    }

    @Nested
    class PositiveTests {
        @Test
        void testIdentityTransform() {
            Vector2D point = new Vector2D(1, 1);
            Vector2D transformedPoint = identityTransform.transform(point);
            assertEquals(new Vector2D(1, 1), transformedPoint);
        }

        @Test
        void testTranslationTransform() {
            Vector2D point = new Vector2D(1, 1);
            Vector2D transformedPoint = translationTransform.transform(point);
            assertEquals(new Vector2D(6, 6), transformedPoint);
        }

        @Test
        void testRotationTransform() {
            Vector2D point = new Vector2D(1, 0);
            Vector2D transformedPoint = rotationTransform.transform(point);
            assertEquals(new Vector2D(0, 1), transformedPoint);
        }
    }
}
