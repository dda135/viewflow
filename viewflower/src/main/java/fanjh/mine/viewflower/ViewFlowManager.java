package fanjh.mine.viewflower;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
* @author fanjh
* @date 2018/1/19 10:43
* @description 视图流模型管理
* @note 之所以是abstract，是为了隐藏一些API
**/
public abstract class ViewFlowManager {

    /**
     * 统一用String来进行管理
     * 好处：相对于
     * @param tag ViewFlow的标记
     *
     * @return null或者唯一的ViewFlow
     */
    public abstract ViewFlow findViewFlowByTag(String tag);

    /**
     * 添加ViewFlow到指定的布局id中
     * add操作只是添加，不会处理去重等操作
     * @param tag 需要添加的ViewFlow的标记
     * @param viewFlow 需要添加的ViewFlow
     * @param launcherMode 启动模式 {@link LauncherMode}
     */
    public abstract void addViewFlow(String tag, ViewFlow viewFlow, @LauncherMode.Mode int launcherMode);

    /**
     * 添加ViewFlow到指定的布局id中
     * add操作只是添加，不会处理去重等操作
     * 默认采用标准的启动模式
     * @param tag 需要添加的ViewFlow的标记
     * @param viewFlow 需要添加的ViewFlow
     */
    public abstract void addViewFlow(String tag, ViewFlow viewFlow);

    /**
     * 移除当前ViewFlow
     * @param viewFlow 需要被移除的ViewFlow
     */
    public abstract void removeViewFlow(ViewFlow viewFlow);

    /**
     * 用来保存数据，这样当“内存恢复”之后可以恢复原来的状态
     * @param outState 用来保存数据
     */
    abstract void saveStated(@NonNull Bundle outState);

    /**
     * 用来恢复数据，当“内存恢复”之后调用
     * @param savedInstanceState 用来恢复数据
     */
    abstract void restoreStated(@NonNull Bundle savedInstanceState);

    /**
     * 用于分发onCreate生命周期
     * @param savedInstanceState 当从“内存恢复”之后才不为null
     */
    abstract void dispatchOnCreate(@Nullable Bundle savedInstanceState);

    /**
     * 用于分发onResume生命周期
     */
    abstract void dispatchOnResume();

    /**
     * 用于分发onPause生命周期
     */
    abstract void dispatchOnPause();

    /**
     * 用于分发onStop生命周期
     */
    abstract void dispatchOnStop();

    /**
     * 用于分发onDestroy生命周期
     * @param activity 当前解绑的activity
     */
    abstract void dispatchOnDestroy(Activity activity);

    /**
     * 用于关联activity
     * @param activity 当前关联的ViewFlowActivity
     */
    abstract void dispatchOnAttach(ViewFlowActivity activity);

    /**
     * 用于分发onTrimMemory生命周期
     * @param level 当前处于的等级
     */
    abstract void dispatchOnTrimMemory(int level);

    /**
     * 用于分发onBackPressed事件
     */
    abstract void dispatchOnBackPressed();

}
