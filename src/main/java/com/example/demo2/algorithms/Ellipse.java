package com.example.demo2.algorithms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;


public class Ellipse {
    final static double epsilon = 1e-10;
    public static void drawFilledEllipse(GraphicsContext gc, double centerX, double centerY, double a, double b, Color startColor, Color endColor) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
        if (a <= 1 || b <= 1) {
            // Если одна из осей равна 1, рисуем линию вместо эллипса
            if (a <= 1) {
                // Вертикальная линия с интерполяцией
                for (double y = -b; y <= b; y++) {
                    double distance = Math.abs(y);
                    double maxDistance = b;
                    Color interpolateColor = interpolateColor(startColor, endColor, distance, maxDistance);
                    gc.setFill(interpolateColor);
                    drawPoint(gc, centerX, centerY + y, canvasWidth, canvasHeight);
                }
            } else {
                // Горизонтальная линия с интерполяцией
                for (double x = -a; x <= a; x++) {
                    double distance = Math.abs(x);
                    double maxDistance = a;
                    Color interpolateColor = interpolateColor(startColor, endColor, distance, maxDistance);
                    gc.setFill(interpolateColor);
                    drawPoint(gc, centerX + x, centerY, canvasWidth, canvasHeight);
                }
            }
        } else {
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
    public static void drawFilledBresenhamEllipse(GraphicsContext gc, int centerX, int centerY, int a, int b, Color startColor, Color endColor) {
        int x = 0;
        int y = b;
        int a2 = a * a;
        int b2 = b * b;
        int twoA2 = 2 * a2;
        int twoB2 = 2 * b2;
        int p = (int) (b2 - (a2 * b) + (0.25 * a2)); // Начальное значение решения

        // Рисуем первую четверть эллипса с заливкой
        while ((twoB2 * x) <= (twoA2 * y)) {
            fillEllipseQuarter(gc, centerX, centerY, x, y, a, b, startColor, endColor);
            x++;
            if (p < 0) {
                p += twoB2 * x + b2;
            } else {
                y--;
                p += twoB2 * x + b2 - twoA2 * y;
            }
        }

        // Переход ко второй части четверти эллипса с заливкой
        p = (int) (b2 * (x + 0.5) * (x + 0.5) + a2 * (y - 1) * (y - 1) - a2 * b2);
        while (y >= 0) {
            fillEllipseQuarter(gc, centerX, centerY, x, y, a, b, startColor, endColor);
            y--;
            if (p > 0) {
                p -= twoA2 * y + a2;
            } else {
                x++;
                p += twoB2 * x - twoA2 * y + a2;
            }
        }
    }
    private static void fillEllipseQuarter(GraphicsContext gc, int centerX, int centerY, int x, int y, int a, int b, Color startColor, Color endColor) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
        for (int dx = 0; dx <= x; dx++) {
            double distance = Math.sqrt((dx * dx) / (double) (a * a) + (y * y) / (double) (b * b));
            double maxDistance = 1.0; // Нормализованное максимальное расстояние до края эллипса

            Color interpolateColor = interpolateColor(startColor, endColor, distance, maxDistance);
            gc.setFill(interpolateColor);

            // Рисуем точки в четырех четвертях
            drawPoint(gc, centerX + dx, centerY + y, canvasWidth, canvasHeight); // Нижняя правая четверть
            drawPoint(gc, centerX - dx, centerY + y, canvasWidth, canvasHeight); // Нижняя левая четверть
            drawPoint(gc, centerX + dx, centerY - y, canvasWidth, canvasHeight); // Верхняя правая четверть
            drawPoint(gc, centerX - dx, centerY - y, canvasWidth, canvasHeight); // Верхняя левая четверть
        }
    }
}
