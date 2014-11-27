package Tasks;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Stefan on 11/27/2014.
 */
public class Converter {
    public List<String> convertStringToList(String text){
        String[] list = text.replaceAll("^[,\\s]+", "").split("[,\\s]+");

        return Arrays.asList(list);
    }


}
