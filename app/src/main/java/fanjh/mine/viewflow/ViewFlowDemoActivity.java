package fanjh.mine.viewflow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import fanjh.mine.viewflower.ViewFlow;
import fanjh.mine.viewflower.ViewFlowActivity;

/**
 * Created by faker on 2018/1/23.
 */

public class ViewFlowDemoActivity extends ViewFlowActivity implements JumpController{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null == savedInstanceState){
            getViewFlowManager().addViewFlow("demo1", FirstActivityViewFlow.getInstance());
        }
    }

    @Override
    public void jump(String tag, ViewFlow viewFlow) {
        getViewFlowManager().addViewFlow(tag,viewFlow);
    }

    @Override
    public void jump(String tag, ViewFlow viewFlow, int mode) {
        getViewFlowManager().addViewFlow(tag,viewFlow,mode);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
