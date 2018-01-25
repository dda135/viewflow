package fanjh.mine.viewflower;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
* @author fanjh
* @date 2018/1/19 11:16
* @description 要正常使用ViewFlow，必须继承当前Activity
* @note
**/
public class ViewFlowActivity extends Activity{
    private ViewFlowManager viewFlowManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initViewFlowManager();
        viewFlowManager.dispatchOnAttach(this);
        super.onCreate(savedInstanceState);
        if(null != savedInstanceState){
            viewFlowManager.restoreStated(savedInstanceState);
        }
        viewFlowManager.dispatchOnCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewFlowManager.dispatchOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewFlowManager.dispatchOnPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewFlowManager.dispatchOnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewFlowManager.dispatchOnDestroy(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewFlowManager.saveStated(outState);
    }

    public ViewFlowManager getViewFlowManager(){
        initViewFlowManager();
        return viewFlowManager;
    }

    private void initViewFlowManager(){
        if(null == viewFlowManager){
            viewFlowManager = new ViewFlowManagerImpl();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        viewFlowManager.dispatchOnTrimMemory(level);
    }

    @Override
    public void onBackPressed() {
        viewFlowManager.dispatchOnBackPressed();
    }

}
