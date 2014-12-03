package Tasks;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.widget.TextView;

import android.os.Handler;

import com.example.stefan.Activities.ApplicationBase;
import com.example.stefan.recipesuggestor.R;

import java.util.logging.LogRecord;

/**
 * Created by Stefan on 12/1/2014.
 */
public class TypeWriterAnimator extends TextView {

    private CharSequence mText;
    private int mIndex;
    private long sDelay;
    private MediaPlayer mPlayer;

    public TypeWriterAnimator(Context context) {
        super(context);

    }

    public TypeWriterAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPlayer = MediaPlayer.create(context, R.raw.penciltyping);

        ApplicationBase appBase = new ApplicationBase();

        sDelay = appBase.getAnimatorDelay();
    }

    public void animateText(CharSequence text){
        this.mText = text;
        this.mIndex = 0;

        setText("");
        mHandler.removeCallbacks(addCharacter);
        mHandler.postDelayed(addCharacter,sDelay);
        mPlayer.start();
    }

    public void setDelay(long milliseconds){
        sDelay = milliseconds;
    }

    private Handler mHandler = new Handler();
    private Runnable addCharacter = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0,mIndex++));
            if (mIndex <= mText.length()){
                mHandler.postDelayed(addCharacter,sDelay);
            }
            else {
                mPlayer.stop();
                mPlayer.release();
            }
        }
    };

    private void playSound(){
        mPlayer.start();

    }
}
