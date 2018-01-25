package fanjh.mine.viewflow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fanjh.mine.viewflower.ViewFlow;
import fanjh.mine.viewflower.ViewFlowActivity;

/**
 * Created by faker on 2018/1/19.
 */

public class DemoViewFlow extends ViewFlow{
    public static final String EXTRA_TEXT = "extra_text";
    private View view;
    private String text;
    private TextView textView;

    public static DemoViewFlow getInstance(String text){
        DemoViewFlow demoViewFlow = new DemoViewFlow();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT,text);
        demoViewFlow.setArguments(bundle);
        return demoViewFlow;
    }

    @Override
    public View onCreate(ViewGroup parent, @Nullable Bundle savedInstanceState) {
        text = getArguments().getString(EXTRA_TEXT);
        Log.i(getClass().getSimpleName(),"arguments-->"+text);
        if(null == view) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.viewflow_demo, parent, false);
        }
        textView = view.findViewById(R.id.tv_text1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(text);
                Intent intent = new Intent(getActivity(),Main1Activity.class);
                getActivity().startActivity(intent);
            }
        });
        Log.i(getClass().getSimpleName(),"onCreate");
        return view;
    }

    @Override
    public void onResume() {
        Log.i(getClass().getSimpleName(),"onResume");
    }

    @Override
    public void onPause() {
        Log.i(getClass().getSimpleName(),"onPause");
    }

    @Override
    public void onStop() {
        Log.i(getClass().getSimpleName(),"onStop");
    }

    @Override
    public void onDestroy() {
        Log.i(getClass().getSimpleName(),"onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(getClass().getSimpleName(),"onSaveInstanceState");
    }
}
