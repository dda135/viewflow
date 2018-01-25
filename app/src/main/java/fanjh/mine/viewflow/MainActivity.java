package fanjh.mine.viewflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fanjh.mine.viewflower.ViewFlow;
import fanjh.mine.viewflower.ViewFlowActivity;

public class MainActivity extends ViewFlowActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(null == savedInstanceState) {
            getViewFlowManager().addViewFlow("demo",DemoViewFlow.getInstance("Hello Word!"));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
