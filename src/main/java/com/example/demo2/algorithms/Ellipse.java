package com.example.demo2.algorithms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Ellipse {
    final static double epsilon = 1e-10;
    public static void drawFilledEllipse(GraphicsContext gc, double centerX, double centerY, double a, double b, Color startColor, Color endColor) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
        // Обходим только 1/4 эллипса
        for (double y = 0; y <= b; y++) {
            // Находим границы по x для текущей строки y
            double dx = a * Math.sqrt(1 - (y*y)/(b*b));
            for (double x = 0; x <= dx; x++) {
                double distance = Math.sqrt((x * x) / (a * a) + (y * y) / (b * b));
                distance = Math.max(0, Math.min(1, distance + epsilon - epsilon));
                double maxDistance = 1.0;
                Color interpolateColor = interpolateColor(startColor, endColor, distance, maxDistance);

                gc.setFill(interpolateColor);

                drawPoint(gc,centerX + x, centerY + y, canvasWidth, canvasHeight); // Нижняя правая четверть
                drawPoint(gc,centerX - x, centerY + y, canvasWidth, canvasHeight); // Нижняя левая четверть
                drawPoint(gc, centerX + x, centerY - y, canvasWidth, canvasHeight); // Верхняя правая четверть
                drawPoint(gc, centerX - x, centerY - y, canvasWidth, canvasHeight); // Верхняя левая четверть
            }
        }
    }
    private static void drawPoint(GraphicsContext gc, double x, double y, double canvasWidth, double canvasHeight) {
        //Проверяем, что точка лежит внутри холста
        if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight) {
            gc.fillRect(x,y,1,1);
        }
    }
    private static Color interpolateColor(Color startColor, Color endColor, double distance, double maxDistance) {
        double ratio = distance / (maxDistance + epsilon);
        ratio = Math.max(0, Math.min(1, ratio + epsilon - epsilon));
        double red = startColor.getRed() * (1 - ratio) + endColor.getRed() * ratio;
        double green = startColor.getGreen() * (1 - ratio) + endColor.getGreen() * ratio;
        double blue = startColor.getBlue() * (1 - ratio) + endColor.getBlue() * ratio;

        return new Color(red, green, blue, 1.0);
    }
}
