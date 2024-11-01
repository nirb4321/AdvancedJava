import java.util.Random;

public class YearData {
    private double[] monthlyTemperatures;

    public YearData() {
        // Initialize temperatures for 12 months with random values
        this.monthlyTemperatures = new double[12];
        Random rand = new Random();
        
        // Generate temperatures between 0 and 30
        for (int i = 0; i < 12; i++) {
            this.monthlyTemperatures[i] = 0 + (30 * rand.nextDouble());
        }
    }

    public double[] getMonthlyTemperatures() {
        return monthlyTemperatures;
    }
}
