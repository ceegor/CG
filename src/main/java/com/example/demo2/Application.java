package com.example.demo2;

import com.example.demo2.algorithms.BresenhamAlgorithm;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) {

        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearCanvas(gc);

        TextField centerX_Field = new TextField();
        TextField centerY_Field = new TextField();
        TextField a = new TextField();
        TextField b = new TextField();


        ColorPicker colorPicker1 = new ColorPicker(Color.BLACK);
        ColorPicker colorPicker2 = new ColorPicker(Color.BLACK);

        Button drawBresenhamButton = new Button("Bresenham");
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


        inputGrid.add(drawBresenhamButton, 3, 3, 2, 1);
        inputGrid.add(clearButton, 1, 5, 2, 1);




        drawBresenhamButton.setOnAction(e -> {
            try {
                double centerX = Double.parseDouble(centerX_Field.getText());
                double centerY = Double.parseDouble(centerY_Field.getText());
                double ellipse_a = Double.parseDouble(a.getText());
                double ellipse_b = Double.parseDouble(b.getText());

                Color color1 = colorPicker1.getValue();
                Color color2 = colorPicker2.getValue();

                BresenhamAlgorithm.drawFilledEllipse(gc, centerX, centerY, ellipse_a, ellipse_b, color1, color2);
            } catch (NumberFormatException ex) {
                System.out.println("Неверный формат координат.");
            }
        });


        clearButton.setOnAction(e -> clearCanvas(gc));


        VBox root = new VBox();
        root.getChildren().addAll(canvas, inputGrid);


        Scene scene = new Scene(root, 600, 700);
        primaryStage.setTitle("Program");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearCanvas(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 500, 500);
        gc.setStroke(Color.BLACK);
    }

}
