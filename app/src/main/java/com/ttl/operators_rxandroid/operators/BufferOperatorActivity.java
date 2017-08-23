package com.ttl.operators_rxandroid.operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import com.ttl.operators_rxandroid.R;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/*
* periodically gather items emitted by an Observable into bundles and emit these bundles rather
 * than emitting the items one at a time.
 * The Buffer operator transforms an Observable that emits items into an Observable that emits buffered
  * collections of those items. There are a number of variants in the various language-specific
   *implementations of Buffer that differ in how they choose which items go in which buffers.
    Note that if the source Observable issues an onError notification, Buffer will pass on this notification
    immediately without first emitting the buffer it is in the process of assembling, even if that buffer
    contains items that were emitted by the source Observable before it issued the error notification.
 * for more information :
 * http://reactivex.io/documentation/operators/buffer.html
* */
public class BufferOperatorActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = BufferOperatorActivity.class.getSimpleName();
    private Button btnBufferOp;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_example);
        btnBufferOp = (Button) findViewById(R.id.btn_test);
        textView  = (TextView) findViewById(R.id.tv_test);
        btnBufferOp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test:
                doBuffer();
                break;
        }
    }

    private void doBuffer(){
        Observable<List<String>> buffered = getObservable().buffer(3,1);
        // 3 means,  it takes max of three from its start index and create list
        // 1 means, it jumps one step every time
        // so the it gives the following list
        // 1 - one, two, three
        // 2 - two, three, four
        // 3 - three, four, five
        // 4 - four, five
        // 5 - five
            buffered.subscribe(getObserver());
    }
    private Observable<String> getObservable() {
        return Observable.just("one", "two", "three", "four", "five");
    }

    private Observer<List<String>> getObserver() {
        return new Observer<List<String>>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(List<String> stringList) {
                textView.append(" onNext size : " + stringList.size());
                textView.append("\n");
                Log.d(TAG, " onNext : size :" + stringList.size());
                for (String value : stringList) {
                    textView.append(" value : " + value);
                    textView.append("\n");
                    Log.d(TAG, " : value :" + value);
                }

            }

            @Override
            public void onError(Throwable e) {
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
