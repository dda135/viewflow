package fanjh.mine.viewflow;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import fanjh.mine.viewflower.LauncherMode;
import fanjh.mine.viewflower.ViewFlow;
import fanjh.mine.viewflower.ViewFlowActivity;

/**
 * Created by faker on 2018/1/23.
 */

public class ThirdActivityViewFlow extends ViewFlow{

    private JumpController jumpController;
    private View view;
    private Button textView;

    public static ThirdActivityViewFlow getInstance(){
        ThirdActivityViewFlow demoViewFlow = new ThirdActivityViewFlow();
        Bundle bundle = new Bundle();
        //bundle.putString(EXTRA_TEXT,text);
        demoViewFlow.setArguments(bundle);
        return demoViewFlow;
    }

    @Override
    protected void onAttach(ViewFlowActivity activity) {
        super.onAttach(activity);
        jumpController = (JumpController) activity;
    }

    @Override
    public View onCreate(ViewGroup parent, @Nullable Bundle savedInstanceState) {
        if(null == view) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_viewflow_third, parent, false);
        }
        textView = view.findViewById(R.id.btn);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(),Main1Activity.class);
                getActivity().startActivity(intent);*/
                jumpController.jump("aa",SecondActivityViewFlow.getInstance(), LauncherMode.STANDARD);
            }
        });
        Log.i(getClass().getSimpleName(),"onCreate");
        return view;
    }

    @Override
    protected void onNewIntent() {
        super.onNewIntent();
        Log.i(getClass().getSimpleName(),"onNewIntent");
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

    @Override
    protected void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getActivity(),"third_onBackPressed",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected Animator getEnterAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(0,1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha(animation.getAnimatedFraction());
                //view.setScaleY(animation.getAnimatedFraction());
            }
        });
        animator.setDuration(300);
        return animator;
    }

}
