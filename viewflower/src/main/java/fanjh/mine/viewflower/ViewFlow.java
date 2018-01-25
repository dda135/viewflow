package fanjh.mine.viewflower;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
* @author fanjh
* @date 2018/1/19 10:48
* @description 视图的基础单位
* @note
**/
public class ViewFlow {
    @LifeCycleState.State int state;
    private ViewFlowActivity activity;
    private Bundle arguments;
    private View view;

    public ViewFlow() {
    }

    public ViewFlowActivity getActivity() {
        return activity;
    }

    protected Bundle getArguments() {
        return arguments;
    }

    public void setArguments(Bundle arguments) {
        this.arguments = arguments;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    protected void onAttach(ViewFlowActivity activity){
        this.activity = activity;
    }
    protected View onCreate(ViewGroup parent, @Nullable Bundle savedInstanceState){return null;}
    protected void onResume(){}
    protected void onPause(){}
    protected void onStop(){}
    protected void onDestroy(){}
    protected void onSaveInstanceState(Bundle outState){}
    protected void onTrimMemory(int level){}
    protected void onBackPressed(){}
    protected void onNewIntent(){}

    public void finish(){
        getActivity().getViewFlowManager().removeViewFlow(this);
    }

    protected Animator getEnterAnimator(){return null;}

}
