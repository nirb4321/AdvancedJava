import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class WeatherMainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Canvas canv;

    @FXML
    private Button btn;
    
    private GraphicsContext gc;
    private List<YearData> yearsData;
    private int currentYearIndex = 0;

    @FXML
    void btnPressed(ActionEvent event) {
        // Move to the next year in the list (wrap around if needed)
        currentYearIndex = (currentYearIndex + 1) % yearsData.size();
        drawBarChart();
    }

    @FXML
    void initialize() {
        assert canv != null : "fx:id=\"canv\" was not injected: check your FXML file 'WeatherMain.fxml'.";
        assert btn != null : "fx:id=\"btn\" was not injected: check your FXML file 'WeatherMain.fxml'.";
        
        gc = canv.getGraphicsContext2D();
        
        // Initialize data for 5 years
        yearsData = new ArrayList<>();
        for (int i = 0; i <= 5; i++) { 
            yearsData.add(new YearData());
        }
        
        // Draw the bar chart for the first year
        drawBarChart();
    }

    private void drawBarChart() {
        gc.clearRect(0, 0, canv.getWidth(), canv.getHeight()); // Clear the canvas

        double[] temperatures = yearsData.get(currentYearIndex).getMonthlyTemperatures();
        int numBars = temperatures.length;
        double barWidth = canv.getWidth() / (numBars * 1.5);  
        double maxBarHeight = canv.getHeight() * 0.7; 

        // Display year label at the top
        gc.setFill(Color.BLACK);
        gc.fillText("Year:  " + (2017 + currentYearIndex), canv.getWidth() / 2 - 10 , 20);

        // Find the highest and lowest temperatures
        double maxTemperature = Double.MIN_VALUE;
        double minTemperature = Double.MAX_VALUE;
        int maxIndex = -1;
        int minIndex = -1;

        for (int i = 0; i < numBars; i++) {
            if (temperatures[i] > maxTemperature) {
                maxTemperature = temperatures[i];
                maxIndex = i;
            }
            if (temperatures[i] < minTemperature) {
                minTemperature = temperatures[i];
                minIndex = i;
            }
        }

        // Draw each bar with appropriate color based on temperature comparison
        for (int i = 0; i < numBars; i++) {
            double barHeight = (temperatures[i] / 30.0) * maxBarHeight; // Scale temperature to fit within max height
            double xPosition = i * barWidth * 1.5;
            double yPosition = canv.getHeight() - barHeight - 20; // Lift bar above month number

            // Set color based on temperature ranking
            if (i == maxIndex) {
                gc.setFill(Color.RED); // Highest temperature
            } else if (i == minIndex) {
                gc.setFill(Color.BLUE); // Lowest temperature
            } else {
                gc.setFill(Color.GRAY); // All other temperatures
            }

            // Draw the rectangle bar
            gc.fillRect(xPosition, yPosition, barWidth, barHeight);

            // Draw the month number below the bar
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(i + 1), xPosition + barWidth / 3, canv.getHeight() - 5);
        }
    }

}
