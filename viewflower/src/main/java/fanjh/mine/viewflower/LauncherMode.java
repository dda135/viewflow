package fanjh.mine.viewflower;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
* @author fanjh
* @date 2018/1/19 16:34
* @description 启动模式
* @note
**/
public class LauncherMode {
    /**
     * 标准模式，直接启动一个新的ViewFlow
     */
    public static final int STANDARD = 0;
    /**
     * 如果当前ViewFlow位于栈顶，不启动新的ViewFlow，回调onNewIntent()
     * 否则启动一个新的
     */
    public static final int SINGLE_TOP = 1;
    /**
     * 如果当前ViewFlow在栈中存在，不启动新的ViewFlow，并恢复之前的ViewFlow，然后回调onNewIntent()
     * 否则启动一个新的
     */
    public static final int SINGLE_TASK = 2;

    @IntDef({STANDARD,SINGLE_TOP,SINGLE_TASK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode{}

}
