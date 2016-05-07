package jp.ac.it_college.std.s14002.android.comikequizgame;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;


public class Result extends AppCompatActivity implements GestureDetector.OnGestureListener  {
    GestureDetector gestureDetector;
    private String recordName;
    private String recordTime;
    private String clearName;
    private String clearTime;
    private PopupWindow mPopupWindow;
    private boolean best = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.gestureDetector = new GestureDetector(this, (GestureDetector.OnGestureListener) this);

        setContentView(R.layout.activity_result);


        loadData();
        clearTime = getIntent().getStringExtra("time");

        comparison();

        setText();

        final Button buttonOk = (Button)findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (best) {
                    popup();
                    buttonOk.setEnabled(false);
                } else {
                    finish();
                }
            }
        });
    }

    private void setText() {
        TextView rName = (TextView)findViewById(R.id.recordName);
        TextView rTime = (TextView)findViewById(R.id.recordTime);
        TextView cTime = (TextView)findViewById(R.id.clearTime);
        rName.setText(recordName);
        rTime.setText(recordTime);
        cTime.setText(clearTime);
    }

    private void saveData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor e = pref.edit();
        e.putString("name", clearName);
        e.putString("time", clearTime);
        e.apply();
    }

    private void loadData() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        recordName = pref.getString("name", "noneName");
        recordTime = pref.getString("time", "noneTime");
    }

    private void comparison() {
        int record = splitTime(recordTime);
        int clear = splitTime(clearTime);
        if (record > clear) {
            //記録更新処理

            best = true;
        }
    }

    private int splitTime(String str) {
        Log.e("log :", str);
        if (!str.equals("noneTime")) {
            String[] times = str.split(":", 0);

            System.out.println(times[0]);
            System.out.println(times[1]);

            int sMinute = Integer.parseInt(times[0]) * 60;
            int second = Integer.parseInt(times[1]);

            int sum = sMinute + second;
            return sum;
        } else {
            return 1000;
        }
    }

    private void popup() {
        mPopupWindow = new PopupWindow(Result.this);

        // レイアウト設定
        final View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);
        popupView.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                    EditText text = (EditText)popupView.findViewById(R.id.input);
                    Log.e("Log",text.getText().toString());
                    clearName = text.getText().toString();
                    saveData();
                    best = false;

                    finish();
                }
            }
        });
        mPopupWindow.setContentView(popupView);

        /*// 背景設定
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));*/

        // タップ時に他のViewでキャッチされないための設定
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);

        // 表示サイズの設定 今回は幅300dp
        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
//        mPopupWindow.setWindowLayoutMode((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth((int) width);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // 画面中央に表示
//        mPopupWindow.showAtLocation(findViewById(R.id.show_button), Gravity.CENTER, 0, 0);
        mPopupWindow.showAtLocation(findViewById(R.id.buttonOk), Gravity.CENTER, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (best) popup();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
