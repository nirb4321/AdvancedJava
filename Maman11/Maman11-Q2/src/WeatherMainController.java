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

    // Create the chart with the correct values
    private void drawBarChart() {
        gc.clearRect(0, 0, canv.getWidth(), canv.getHeight()); // Clear the canvas

        double[] temperatures = yearsData.get(currentYearIndex).getMonthlyTemperatures();
        int numBars = temperatures.length;
        double barWidth = (canv.getWidth() - 50) / (numBars * 1.5); // Adjust for left margin
        double maxBarHeight = canv.getHeight() * 0.7;
        double leftMargin = 50; // Space on the left for tick marks and labels

        // Display year label at the top
        gc.setFill(Color.BLACK);
        gc.fillText("Year: " + (2017 + currentYearIndex), canv.getWidth() / 2 - 10, 20);

        // Draw temperature scale (tick marks) on the left
        int numTicks = 6; // Number of tick marks
        double maxTemperature = 30.0; // Assume max temperature for scaling
        double tickSpacing = maxBarHeight / numTicks;

        for (int i = 0; i <= numTicks; i++) {
            double tickY = canv.getHeight() - 20 - i * tickSpacing;
            double temperatureValue = i * (maxTemperature / numTicks);
            gc.setFill(Color.BLACK);
            gc.fillText(String.format("%.0f°C", temperatureValue), leftMargin - 40, tickY + 5); // Draw tick labels
            gc.strokeLine(leftMargin - 10, tickY, leftMargin, tickY); // Draw tick lines
        }

        // Find the indices of the lowest and highest temperatures
        double minTemperature = Double.MAX_VALUE;
        double maxTemperatureValue = Double.MIN_VALUE;
        int minIndex = -1;
        int maxIndex = -1;

        for (int i = 0; i < numBars; i++) {
            if (temperatures[i] < minTemperature) {
                minTemperature = temperatures[i];
                minIndex = i;
            }
            if (temperatures[i] > maxTemperatureValue) {
                maxTemperatureValue = temperatures[i];
                maxIndex = i;
            }
        }

        // Draw each bar and temperature value at the top
        for (int i = 0; i < numBars; i++) {
            double barHeight = (temperatures[i] / maxTemperature) * maxBarHeight; // Scale temperature to fit within max height
            double xPosition = leftMargin + i * barWidth * 1.5;
            double yPosition = canv.getHeight() - barHeight - 20; // Lift bar above tick marks

            // Set color: Red for highest, Blue for lowest, Gray otherwise
            if (i == maxIndex) {
                gc.setFill(Color.RED); // Highest temperature
            } else if (i == minIndex) {
                gc.setFill(Color.BLUE); // Lowest temperature
            } else {
                gc.setFill(Color.GRAY); // Other temperatures
            }

            // Draw the rectangle bar
            gc.fillRect(xPosition, yPosition, barWidth, barHeight);

            // Draw the temperature value at the top of the bar
            gc.setFill(Color.BLACK);
            gc.fillText(String.format("%.1f", temperatures[i]), xPosition + barWidth / 4, yPosition - 5);

            // Draw the month number below the bar
            gc.fillText(String.valueOf(i + 1), xPosition + barWidth / 3, canv.getHeight() - 5);
        }
    }

}
