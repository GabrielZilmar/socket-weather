import java.util.Random;

/**
 * @author Erick Lage e Gabriel Zilmar
 * @version 04/04/2021
 */

/**
 * Classe Weather
 */
public class Weather {
    private String status;
    private String[] possibleWeather = { "Sunny", "Cloudy", "Partially cloudy", "Raining", "Snowing" };
    private int temperature;
    private Random r;

    Weather() {
        r = new Random();
        changeWeather();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    /**
     * Metodo que altera de forma randomica o clima
     */
    void changeWeather() {
        int n = r.nextInt(5);
        status = possibleWeather[n];
        switch (n) {
        case 0:
            temperature = r.nextInt(26) + 10; // between 10 and 35
            break;
        case 1:
            temperature = r.nextInt(31) + 0; // between 0 and 30
            break;
        case 2:
            temperature = r.nextInt(36) + 0; // between 0 and 35
            break;
        case 3:
            temperature = r.nextInt(21) + 0; // between 0 and 20
            break;
        case 4:
            temperature = r.nextInt(20) - 20; // between -20 and 0
            break;
        }
    }

}