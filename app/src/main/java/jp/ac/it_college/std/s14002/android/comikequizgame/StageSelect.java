package jp.ac.it_college.std.s14002.android.comikequizgame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class StageSelect extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        // ボタン処理呼び出し
        setIcon();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stage_select);
    }

    // ボタンをクリア済みかどうかで色分け&クリック不可処理
    private void setIcon(){
        // 作成したDatabaseHelperクラスに読み込み専用でアクセス
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String sql = "SELECT Clear FROM MyTable";

        //上記のSELECT文を実行してカーソルを取得
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();

        // CLEAR_FLAGを入れる配列(要素数はデータの数だけ)を作成
        int[] ClearFlg = new int[cursor.getCount()];

        // クリア状況取得して配列CLEAR_FLAGに入れていく
        for (int i = 0; i < cursor.getCount(); i++) {
            ClearFlg[i] = cursor.getInt(0);
            cursor.moveToNext();
        }
        // データベースからデータを撮り終わったのでクローズ処理
        cursor.close();
        database.close();

        for (int i = 1; i<= 10; i++) {
            // layoutでbuttonのidをbutton1~10で連番を振っているのでそれを利用する
            String resViewName = "button" + i;
            int viewId = getResources().getIdentifier(resViewName, "id", getPackageName());
            Button button = (Button)findViewById(viewId);
            // Clearの値によって処理を分ける
            button.setText(String.valueOf(i));
            /*if (i == 1) {
                button.setText("1");
            } else if (i == 2) {
                button.setText("11");
            } else if (i == 3) {
                button.setText("21");
            } else if (i == 4) {
                button.setText("31");
            } else if (i == 5){
                button.setText("41");
            } else if (i == 6) {
                button.setText("51");
            } else if (i == 7) {
                button.setText("61");
            } else if (i == 8) {
                button.setText("71");
            } else if (i == 9) {
                button.setText("81");
            } else if (i == 10) {
                button.setText("91");
            }*/
            button.setTextColor(0xff33ffcc); //テキストの色
            button.setBackgroundColor(0xffffffff); // ボタンの色
            button.setTextSize(50); // テキストの文字のサイズ
            button.setEnabled(true); // ボタン選択可能にする
        }

    }

    // ボタンクリックでゲーム画面へ遷移
    public void onClick(View v) {
        Intent intent = new Intent(StageSelect.this, NormalGame.class);
        // 選択されたステージをボタンのテキストから取得
        intent.putExtra("QuestionNo", ((Button) v).getText());
        startActivity(intent);
    }
}