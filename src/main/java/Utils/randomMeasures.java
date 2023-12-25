package Utils;

import java.util.Random;

public class randomMeasures {

    /*
    This class, simulate random measures like temperature and brightness.
     */

    /*
    Simulate temperature value
     */
    public int getTemperature(){
        int temperature;
        Random random = new Random();
        temperature = random.nextInt(100);
        return temperature;
    }

    /*
    Simulate brightness value
     */
    public int getBrightness(){
        int brightness;
        Random random = new Random();
        brightness = random.nextInt(100);
        return brightness;
    }
}
