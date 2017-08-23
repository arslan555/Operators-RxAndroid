package com.ttl.operators_rxandroid.operators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;
import com.ttl.operators_rxandroid.R;

public class AsyncSubjectActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = AsyncSubjectActivity.class.getSimpleName();
    private Button btnAsyncSubject;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_example);
        btnAsyncSubject = (Button) findViewById(R.id.btn_test);
        textView = (TextView) findViewById(R.id.tv_test);
        btnAsyncSubject.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test:
                doAsyncSubject();
                break;
        }
    }
    private void doAsyncSubject(){
        AsyncSubject<Integer> source =  AsyncSubject.create();
        source.subscribe(firstObserver());
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        //source.onComplete();
        source.subscribe(secondObserver());
        source.onNext(5);
        source.onComplete();
    }

    private Observer<Integer> firstObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " First onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                textView.append(" First onNext : value : " + value);
                textView.append("\n");
                Log.d(TAG, " First onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" First onError : " + e.getMessage());
                textView.append("\n");
                Log.d(TAG, " First onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" First onComplete");
                textView.append("\n");
                Log.d(TAG, " First onComplete");
            }
        };
    }
    private Observer<Integer> secondObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                textView.append(" Second onSubscribe : isDisposed :" + d.isDisposed());
                Log.d(TAG, " Second onSubscribe : " + d.isDisposed());
                textView.append("\n");
            }

            @Override
            public void onNext(Integer value) {
                textView.append(" Second onNext : value : " + value);
                textView.append("\n");
                Log.d(TAG, " Second onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" Second onError : " + e.getMessage());
                textView.append("\n");
                Log.d(TAG, " Second onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" Second onComplete");
                textView.append("\n");
                Log.d(TAG, " Second onComplete");
            }
        };
    }
}
