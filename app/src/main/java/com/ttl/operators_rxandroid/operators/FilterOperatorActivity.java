package com.ttl.operators_rxandroid.operators;

import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ttl.operators_rxandroid.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

public class FilterOperatorActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = FilterOperatorActivity.class.getSimpleName();
    private Button btnFilterOp;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_example);
        btnFilterOp = (Button) findViewById(R.id.btn_test);
        textView = (TextView) findViewById(R.id.tv_test);
        btnFilterOp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.btn_test:
                doFilter();
                break;
        }
    }

    private void doFilter() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(getObserver());
    }

    private Observer<Integer> getObserver(){
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "on Subscribe "+d);
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                textView.append(" onNext : ");
                textView.append("\n");
                textView.append(" value : " + integer);
                textView.append("\n");
                Log.d(TAG, " onNext ");
                Log.d(TAG, " value : " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                textView.append(" onError : " + e.getMessage());
                textView.append("\n");
                Log.d(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" onComplete");
                textView.append("\n");
                Log.d(TAG, " onComplete");
            }
        };
    }
}
