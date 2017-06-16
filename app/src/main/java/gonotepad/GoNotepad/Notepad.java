package gonotepad.GoNotepad;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * Created by rtb on 14/6/17.
 */

public class Notepad extends AppCompatEditText {

    private Paint paint;

    public Notepad(Context context) {
        super(context);

        initpaint();
    }

    public Notepad(Context context, AttributeSet attrs) {
        super(context, attrs);

        initpaint();
    }

    private void initpaint() {
        paint = new Paint();
        paint.setStrokeWidth(2.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.BLACK);
//        for(int i=50;i<=getHeight();i += 40) {
//            canvas.drawLine(5, i, getWidth(), i, paint);
//        }

        setGravity(Gravity.TOP | Gravity.LEFT);
    }
}
