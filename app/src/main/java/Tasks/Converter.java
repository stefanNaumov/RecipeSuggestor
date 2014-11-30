package Tasks;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Stefan on 11/27/2014.
 */
public class Converter {
    public List<String> convertStringToList(String text){
        String[] list = text.split("\\,");

        return Arrays.asList(list);
    }

    public String convertListToString(List<String> list){

        String joinedList = this.join(list,",");

        return joinedList;
    }

    private static String join(List<String> list, String delimeter) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = list.iterator();
        if (iterator.hasNext())
            sb.append(iterator.next().toString());
        while (iterator.hasNext()) {
            sb.append(delimeter);
            sb.append(iterator.next().toString());
        }

        return sb.toString();
    }

}
