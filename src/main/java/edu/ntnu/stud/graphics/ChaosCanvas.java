package edu.ntnu.stud.graphics;

import edu.ntnu.stud.models.AffineTransform2D;
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
        this.canvas = new int[width][height];
    }

    public int getPixel(Vector2D point) {
        Vector2D transformedPoint = transformCoordsToIndices.transform(point);
        int x = (int) transformedPoint.getX0();
        int y = (int) transformedPoint.getX1();
        return canvas[x][y];
    }

    public void putPixel(Vector2D point) {
        Vector2D transformedPoint = transformCoordsToIndices.transform(point);
        int x = (int) transformedPoint.getX0();
        int y = (int) transformedPoint.getX1();
        canvas[x][0] = x;
        canvas[0][y] = y;
    }

    public void clear() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                canvas[i][j] = 0;
            }
        }
    }
}
