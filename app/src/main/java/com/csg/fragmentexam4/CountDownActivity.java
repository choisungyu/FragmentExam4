package com.csg.fragmentexam4;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Override
    protected void onDestroy() {
        mCountDownTask.cancel(true);
        mCountDownTask = null;
        super.onDestroy();
    }

    static class CountDownEvent{
        int count;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCountDownEvent(CountDownEvent event) {
        mTextNum.setText(String.valueOf(event.count));
        mCountDownFragment.setCount(event.count);
    };

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    static class CountDownTask extends AsyncTask<Void, Integer, Void> {
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

            CountDownEvent event = new CountDownEvent();
            event.count = values[0];
            EventBus.getDefault().post(event);


        }
    }
}
