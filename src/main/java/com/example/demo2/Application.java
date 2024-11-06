package com.example.demo2;

import com.example.demo2.algorithms.Ellipse;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.IllegalFormatException;

public class Application extends javafx.application.Application {

    private Canvas canvas;
    private GraphicsContext gc;
    private StackPane canvasContainer;

    @Override
    public void start(Stage primaryStage) {

        canvas = new Canvas(1000, 500);
        gc = canvas.getGraphicsContext2D();
        clearCanvas(gc);

        canvasContainer = new StackPane(canvas);
        canvasContainer.setMinSize(1000,500);

        TextField centerX_Field = new TextField();
        TextField centerY_Field = new TextField();
        TextField a = new TextField();
        TextField b = new TextField();


        ColorPicker colorPicker1 = new ColorPicker(Color.BLACK);
        ColorPicker colorPicker2 = new ColorPicker(Color.BLACK);

        Button ellipse = new Button("Нарисовать эллипс");
        Button ellipseBresenham = new Button("Нарисовать с помощью Брезенхема");
        Button clearButton = new Button("Очистить холст");

        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(10));
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        inputGrid.add(new Label("Координата X центра:"), 0, 0);
        inputGrid.add(centerX_Field, 1, 0);
        inputGrid.add(new Label("Координата Y центра:"), 2, 0);
        inputGrid.add(centerY_Field, 3, 0);
        inputGrid.add(new Label("a:"), 0, 1);
        inputGrid.add(a, 1, 1);
        inputGrid.add(new Label("b:"), 2, 1);
        inputGrid.add(b, 3, 1);

        inputGrid.add(new Label("Цвет 1:"), 0, 2);
        inputGrid.add(colorPicker1, 1, 2);

        inputGrid.add(new Label("Цвет 2:"), 2, 2);
        inputGrid.add(colorPicker2, 3, 2);


        inputGrid.add(ellipse, 3, 3, 2, 1);
        inputGrid.add(ellipseBresenham, 4,3,2,1);
        inputGrid.add(clearButton, 1, 5, 2, 1);




        ellipse.setOnAction(e -> {
            try {
                double centerX = Double.parseDouble(centerX_Field.getText());
                double centerY = Double.parseDouble(centerY_Field.getText());
                double ellipse_a = Double.parseDouble(a.getText());
                double ellipse_b = Double.parseDouble(b.getText());

                Color color1 = colorPicker1.getValue();
                Color color2 = colorPicker2.getValue();

                adjustCanvasSize(centerX, centerY, ellipse_a, ellipse_b);
                clearCanvas(gc);
                Ellipse.drawFilledEllipse(gc, centerX, centerY, ellipse_a, ellipse_b, color1, color2);
            } catch (IllegalFormatException ex) {
                System.out.println("Неверный формат координат.");
            }
        });
        ellipseBresenham.setOnAction(e -> {
            try {
                int centerX = Integer.parseInt(centerX_Field.getText());
                int centerY = Integer.parseInt(centerY_Field.getText());
                int ellipse_a = Integer.parseInt(a.getText());
                int ellipse_b = Integer.parseInt(b.getText());

                Color color1 = colorPicker1.getValue();
                Color color2 = colorPicker2.getValue();

                adjustCanvasSize(centerX, centerY, ellipse_a, ellipse_b);
                clearCanvas(gc);
                Ellipse.drawFilledBresenhamEllipse(gc, centerX, centerY, ellipse_a, ellipse_b, color1, color2);
            } catch (IllegalFormatException ex) {
                System.out.println("Неверный формат координат.");
            }
        });


        clearButton.setOnAction(e -> clearCanvas(gc));


        VBox root = new VBox();
        root.getChildren().addAll(canvasContainer, inputGrid);
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        double height = Screen.getPrimary().getBounds().getHeight();
        double width = Screen.getPrimary().getBounds().getWidth();
        Scene scene = new Scene(scrollPane, width - 100, height - 100);
        primaryStage.setTitle("Program");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearCanvas(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
    }

    private void adjustCanvasSize(double centerX, double centerY, double a, double b) {
        double requiredWidth = centerX + a + 20;
        double requiredHeight = centerY + b + 20;

        if (requiredWidth > canvas.getWidth() || requiredHeight > canvas.getHeight()) {
            double newWidth = Math.max(canvas.getWidth(), requiredWidth);
            double newHeight = Math.max(canvas.getHeight(), requiredHeight);
            canvas.setWidth(newWidth);
            canvas.setHeight(newHeight);
            canvasContainer.setMinSize(newWidth, newHeight);
            clearCanvas(gc);
        }
    }

}
