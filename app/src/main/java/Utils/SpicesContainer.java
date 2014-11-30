package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan on 11/30/2014.
 */
/*class used for storing the spices list - if the user goes back from the addSpicesActivity
    and then returns, the added spices will be loaded*/
public class SpicesContainer {
    private static SpicesContainer instance;
    private static List<String> spicesList;

    public static SpicesContainer getInstance(){
        if (instance == null){
            instance = new SpicesContainer();
            spicesList = new ArrayList<String>();
        }
        return instance;
    }

    public List<String> getSpicesList(){
        return spicesList;
    }

    public void AddSpice(String spice){
        if (spicesList == null){
            return;
        }

        spicesList.add(spice);
    }

    public void clearList(){
        spicesList.clear();
    }
}
