package fanjh.mine.viewflow;

import android.os.Bundle;

import fanjh.mine.viewflower.ViewFlowActivity;

public class Main1Activity extends ViewFlowActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(null == savedInstanceState) {
            getViewFlowManager().addViewFlow("demo", new Demo1ViewFlow());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
