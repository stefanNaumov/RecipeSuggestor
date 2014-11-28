package Tasks;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Stefan on 11/28/2014.
 */
public class KeyboardHider {
    private Context ctx;
    public KeyboardHider(Context context){
        ctx = context;
    }
    public void hideKeyobard(View view){
        InputMethodManager in = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
