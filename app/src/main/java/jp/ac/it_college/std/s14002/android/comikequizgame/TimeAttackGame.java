package jp.ac.it_college.std.s14002.android.comikequizgame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TimeAttackGame extends AppCompatActivity {

    private Timer mainTimer;                    //タイマー用
    private MainTimerTask mainTimerTask;        //タイマタスククラス
    private TextView countText;                    //テキストビュー
    private int count = 0;                        //カウント
    private Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack_game);

        //タイマーインスタンス生成
        this.mainTimer = new Timer();
        //タスククラスインスタンス生成
        this.mainTimerTask = new MainTimerTask();
        //タイマースケジュール設定＆開始
        this.mainTimer.schedule(mainTimerTask, 1000, 1000);
        //テキストビュー
        this.countText = (TextView) findViewById(R.id.count_text);

    }


    /**
     * タイマータスク派生クラス
     * run()に定周期で処理したい内容を記述
     */
    public class MainTimerTask extends TimerTask {
        @Override
        public void run() {
            //ここに定周期で実行したい処理を記述します
            mHandler.post(new Runnable() {
                public void run() {

                    //実行間隔分を加算処理
                    count += 1;
                    //画面にカウントを表示
                    countText.setText(String.valueOf(count));
                }
            });
        }
    }
}

