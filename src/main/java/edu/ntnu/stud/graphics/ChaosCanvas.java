package edu.ntnu.stud.graphics;

import edu.ntnu.stud.models.AffineTransform2D;
import edu.ntnu.stud.models.Matrix2x2;
import edu.ntnu.stud.models.Vector2D;


public class ChaosCanvas {
    private int[][] canvas;
    private int width;
    private int height;
    private Vector2D minCoords;
    private Vector2D maxCoords;
    private AffineTransform2D transformCoordsToIndices;

    ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords) {
        this.width = width;
        this.height = height;
        this.minCoords = minCoords;
        this.maxCoords = maxCoords;
        this.canvas = new int[height][width];
        transformCoordsToIndices = setTransformCoordsMatrix();
    }

    public int getPixel(Vector2D point) {
        Vector2D transformedPoint = transformCoordsToIndices.transform(point);
        int x = (int) transformedPoint.getX0();
        int y = (int) transformedPoint.getX1();
        return canvas[y][x];
    }

    public void putPixel(Vector2D point) {
        Vector2D transformedPoint = transformCoordsToIndices.transform(point);
        int x = (int) transformedPoint.getX0();
        int y = (int) transformedPoint.getX1();
        canvas[y][x]=1;
    }

    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = 0;
            }
        }
    }

    public AffineTransform2D setTransformCoordsMatrix() {
        double a01 =(height-1)/(minCoords.getX1()- maxCoords.getX1());
        double a10 = (width-1)/(maxCoords.getX0()- minCoords.getX0());

        Matrix2x2 transformMatrix = new Matrix2x2(0, a01, a10,0);

        double x0 = ((height-1)* maxCoords.getX1())/(maxCoords.getX1()- minCoords.getX1());
        double x1 = ((width-1)* minCoords.getX0())/(minCoords.getX0()- maxCoords.getX0());

        Vector2D transformVector = new Vector2D(x0, x1);

        return new AffineTransform2D(transformMatrix, transformVector);
    }
}
