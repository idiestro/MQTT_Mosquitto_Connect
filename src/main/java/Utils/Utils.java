package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

public class Utils {
    public static Properties prop;

    /*
    Read and work with config.properties
    */
    public static Properties getConfigProperties() throws Exception {
        try {
            prop = new Properties();
            prop.load(new FileInputStream("config.properties"));

            return prop;
        } catch (Exception e) {
            throw new Exception("No se encuentra el archivo de configuración: config.properties");

        }
    }

    /*
    Save data into JSON
     */
    public static String createJsonPayload(HashMap<String,String> inputData){
        ObjectMapper objectMapper = new ObjectMapper();
        String outputData;
        try {
            outputData = objectMapper.writeValueAsString(inputData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return outputData;
    }
}
