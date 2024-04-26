package edu.ntnu.stud.models.mathematics;

import edu.ntnu.stud.models.mathematics.Complex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComplexTest {

    private Complex positiveRealPart;
    private Complex negativeRealPart;
    private Complex complexNumber;
    @BeforeEach
    public void setUp() {
        // Set up the test environment
        positiveRealPart = new Complex(4, 0);
        negativeRealPart = new Complex(-4, 0);
        complexNumber = new Complex(4, 9);
    }

    @Test
    public void testSqrtPositiveRealPart() {
        Complex sqrt = positiveRealPart.sqrt();
        assertEquals(2, sqrt.getX0(),0.001);
        assertEquals(0, sqrt.getX1(),0.001);
    }

    @Test
    public void testSqrtNegativeRealPart() {
        Complex sqrt = negativeRealPart.sqrt();
        assertEquals(0, sqrt.getX0(),0.001);
        assertEquals(2, sqrt.getX1(),0.001);
    }

    @Test
    public void testSqrtComplexNumber() {
        Complex sqrt = complexNumber.sqrt();
        assertEquals(2.631, sqrt.getX0(),0.001);
        assertEquals(1.710, sqrt.getX1(),0.001);
    }

}
