package fanjh.mine.viewflower;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
* @author fanjh
* @date 2018/1/19 16:34
* @description 生命周期状态
* @note
**/
public class LifeCycleState {
    public static final int STATE_CREATE = 1;
    public static final int STATE_RESUME = 2;
    public static final int STATE_PAUSE = 3;
    public static final int STATE_STOP = 4;
    public static final int STATE_DESTROY = 5;

    @IntDef({STATE_CREATE,STATE_RESUME,STATE_PAUSE,STATE_STOP,STATE_DESTROY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State{}

}
