package com.ttl.operators_rxandroid.operators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.*;
import android.util.Log;
import com.ttl.operators_rxandroid.R;


/* When an observer subscribes to a BehaviorSubject, it begins by emitting the item most
    * recently emitted by the source Observable (or a seed/default value if none has yet been
    * emitted) and then continues to emit any other items emitted later by the source Observable(s).
    * It is different from Async Subject as async emits the last value (and only the last value)
    * but the Behavior Subject emits the last and the subsequent values also.
    * for more information :
    * http://reactivex.io/RxJava/javadoc/rx/subjects/BehaviorSubject.html
    */
public class BehavoiurSubjectActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = AsyncSubjectActivity.class.getSimpleName();
    private Button btnBehaviourSubject;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_example);
        btnBehaviourSubject = (Button) findViewById(R.id.btn_test);
        textView = (TextView) findViewById(R.id.tv_test);
        btnBehaviourSubject.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test:
            doBeahaviouSubject();
                break;
        }

    }

    private void doBeahaviouSubject(){
        BehaviorSubject<Integer> source = BehaviorSubject.create();
        source.subscribe(firstObserver()); // it will get 1, 2, 3, 4 and onComplete
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
      

        /*
         * it will emit 3(last emitted), 4 and onComplete for second observer also.
         */
        source.subscribe(secondObserver());

        source.onNext(4);
        source.onComplete();

    }
    private Observer<Integer> firstObserver(){
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
