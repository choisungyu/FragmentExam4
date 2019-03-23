package com.csg.fragmentexam4;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CountDownActivity extends AppCompatActivity implements CountDownFragment.OnCountDownFragmentListener {

    private TextView mTextNum;
    private CountDownFragment mCountDownFragment;
    private CountDownTask mCountDownTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);

        mTextNum = findViewById(R.id.text_number);

        if (savedInstanceState == null) {

            mCountDownFragment = new CountDownFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frag_countdown, mCountDownFragment)
                    .commit();
        }
    }

    @Override
    public void onStartButtonClicked() {
        mCountDownTask = new CountDownTask();
        mCountDownTask.execute();
    }

    @Override
    public void onResetButtonClicked() {
        mCountDownTask.cancel(true);
        mCountDownTask = null;
        mTextNum.setText("0");
        mCountDownFragment.setCount(0);
    }

    class CountDownTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i <= 10; i++) {
                try {
                    Thread.sleep(100);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            mTextNum.setText(String.valueOf(values[0]));
            mCountDownFragment.setCount(values[0]);
        }
    }
}
