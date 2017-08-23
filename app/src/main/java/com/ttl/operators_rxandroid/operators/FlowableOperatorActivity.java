package com.ttl.operators_rxandroid.operators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import com.ttl.operators_rxandroid.R;


/*
*
* You may use Observable for example:
  handling GUI events
  working with short sequences (less than 1000 elements total)
  You may use Flowable for example:
  cold and non-timed sources
  generator like sources
  network and database accessors
*
* */

public class FlowableOperatorActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = FlowableOperatorActivity.class.getSimpleName();
    private Button btnFlowableOperator;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_example);
        btnFlowableOperator = (Button) findViewById(R.id.btn_test);
        textView = (TextView) findViewById(R.id.tv_test);
        btnFlowableOperator.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test:
                doFLowableTask();
                break;
        }
    }
    private void doFLowableTask() {

        Flowable<Integer> observable = Flowable.just(1, 2, 3, 4);

        observable.reduce(null, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer t1, Integer t2) {
                return t1 + t2;
            }
        }).subscribe(getObserver());

    }
    private SingleObserver<Integer> getObserver() {

        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(Integer value) {
                textView.append(" onSuccess : value : " + value);
                textView.append("\n");
                Log.d(TAG, " onSuccess : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" onError : " + e.getMessage());
                textView.append("\n");
                Log.d(TAG, " onError : " + e.getMessage());
            }
        };
    }
}
