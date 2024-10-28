package com.example.demo2.algorithms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class BresenhamAlgorithm {
    public static void drawFilledEllipse(GraphicsContext gc, double centerX, double centerY, double a, double b, Color startColor, Color endColor) {
        // Обходим только 1/4 эллипса
        for (double y = 0; y <= b; y++) {
            // Находим границы по x для текущей строки y
            double dx = a * Math.sqrt(1 - (y*y)/(b*b));
            for (double x = 0; x <= dx; x++) {
                // Интерполируем цвет на основе расстояния от центра
                double distance = Math.sqrt(x * x + y * y);
                double maxDistance = Math.sqrt(a * a + b * b);
                Color interpolateColor = interpolateColor(startColor, endColor, distance, maxDistance);

                gc.setFill(interpolateColor);

                gc.fillRect(centerX + x, centerY + y, 1, 1); // нижняя правая четверть
                gc.fillRect(centerX - x, centerY + y, 1, 1); // нижняя левая четверть
                gc.fillRect(centerX + x, centerY - y, 1, 1); // верхняя правая четверть
                gc.fillRect(centerX - x, centerY - y, 1, 1); // верхняя левая четверть
            }
        }
    }
    private static Color interpolateColor(Color startColor, Color endColor, double distance, double maxDistance) {
        double ratio = distance / maxDistance;
        double red = startColor.getRed() * (1 - ratio) + endColor.getRed() * ratio;
        double green = startColor.getGreen() * (1 - ratio) + endColor.getGreen() * ratio;
        double blue = startColor.getBlue() * (1 - ratio) + endColor.getBlue() * ratio;

        return new Color(red, green, blue, 1.0);
    }
}
