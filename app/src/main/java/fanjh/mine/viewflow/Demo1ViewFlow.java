package fanjh.mine.viewflow;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fanjh.mine.viewflower.ViewFlow;
import fanjh.mine.viewflower.ViewFlowActivity;

/**
 * Created by faker on 2018/1/19.
 */

public class Demo1ViewFlow extends ViewFlow{
    private View view;

    @Override
    public View onCreate(ViewGroup parent, @Nullable Bundle savedInstanceState) {
        if(null == view) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.viewflow_demo, parent, false);
        }
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
