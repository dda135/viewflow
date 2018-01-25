package fanjh.mine.viewflower;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimationUtilsCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author fanjh
 * @date 2018/1/19 11:05
 * @description 视图流管理实现类
 * @note
 **/
public class ViewFlowManagerImpl extends ViewFlowManager {
    public static final String EXTRA_VIEWFLOW_MANAGER_STATE = "extra_viewflow_manager_state";
    private List<StackEntry> stackEntries;
    private @LifeCycleState.State
    int state;
    private ViewFlowActivity activity;
    private Bundle savedInstanceState;
    private ViewGroup decorView;

    public ViewGroup getDecorView() {
        if (null != activity && null == decorView) {
            decorView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        }
        return decorView;
    }

    public ViewFlowManagerImpl() {
        stackEntries = new ArrayList<>();
    }

    @Override
    public ViewFlow findViewFlowByTag(String tag) {
        if (null == tag) {
            return null;
        }
        for (StackEntry stackEntry : stackEntries) {
            if (tag.equals(stackEntry.tag)) {
                return stackEntry.viewFlow;
            }
        }
        return null;
    }

    private int searchLastViewFlow(ViewFlow viewFlow){
        int count = stackEntries.size();
        for(int i = count - 1;i >= 0;--i){
            StackEntry stackEntry = stackEntries.get(i);
            if(stackEntry.viewFlow.getClass().getSimpleName().equals(viewFlow.getClass().getSimpleName())){
                return i;
            }
        }
        return -1;
    }

    private int searchFirstViewFlow(ViewFlow viewFlow){
        int count = stackEntries.size();
        for(int i = 0;i < count;++i){
            StackEntry stackEntry = stackEntries.get(i);
            if(stackEntry.viewFlow.getClass().getSimpleName().equals(viewFlow.getClass().getSimpleName())){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addViewFlow(String tag, ViewFlow viewFlow, int launcherMode) {
        int index;
        ViewFlow oldViewFlow = null;
        switch (launcherMode) {
            case LauncherMode.SINGLE_TASK:
                index = searchFirstViewFlow(viewFlow);
                if(index != -1){
                    oldViewFlow = stackEntries.get(index).viewFlow;
                    oldViewFlow.setArguments(viewFlow.getArguments());
                    oldViewFlow.onNewIntent();
                    if(index != stackEntries.size() - 1) {
                        getDecorView().removeView(stackEntries.get(stackEntries.size()-1).viewFlow.getView());
                        getDecorView().addView(oldViewFlow.getView());
                    }
                    changeToState(oldViewFlow, state);
                    int count = stackEntries.size();
                    for(int i = count - 1;i > index;--i){
                        stackEntries.remove(i);
                    }
                }else{
                    addViewFlow(tag, viewFlow, LauncherMode.STANDARD);
                }
                break;
            case LauncherMode.SINGLE_TOP:
                index = searchLastViewFlow(viewFlow);
                if(index != stackEntries.size() - 1){
                    addViewFlow(tag, viewFlow, LauncherMode.STANDARD);
                }else{
                    oldViewFlow = stackEntries.get(index).viewFlow;
                    oldViewFlow.setArguments(viewFlow.getArguments());
                    oldViewFlow.onNewIntent();
                    changeToState(oldViewFlow,state);
                }
                break;
            case LauncherMode.STANDARD:
                if(stackEntries.size() > 0){
                    oldViewFlow = stackEntries.get(stackEntries.size() - 1).viewFlow;
                }
                StackEntry stackEntry = new StackEntry(viewFlow, tag);
                stackEntries.add(stackEntry);
                viewFlow.onAttach(activity);
                if(null != oldViewFlow){
                    moveToState(oldViewFlow,LifeCycleState.STATE_STOP,false);
                    getDecorView().removeView(oldViewFlow.getView());
                }
                moveToState(viewFlow, state, true);
                break;
            default:
                break;
        }
    }

    @Override
    public void addViewFlow(String tag, ViewFlow viewFlow) {
        addViewFlow(tag, viewFlow, LauncherMode.STANDARD);
    }

    @Override
    public void removeViewFlow(ViewFlow viewFlow) {
        if (null == viewFlow) {
            return;
        }
        int index = searchLastViewFlow(viewFlow);
        if(index == -1){
            return;
        }
        if(viewFlow.state >= LifeCycleState.STATE_CREATE) {
            moveToState(viewFlow, LifeCycleState.STATE_DESTROY,false);
        }
        boolean shouldChangeView = (index == stackEntries.size() - 1);
        stackEntries.remove(index);
        if(shouldChangeView){
            ViewGroup parent = getDecorView();
            if (null != getDecorView()) {
                parent.removeView(viewFlow.getView());
            }
            if(stackEntries.size() == 0){
                return;
            }
            ViewFlow showViewFlow = stackEntries.get(stackEntries.size() - 1).viewFlow;
            getDecorView().addView(showViewFlow.getView());
            changeToState(showViewFlow,LifeCycleState.STATE_RESUME);
        }
    }

    @Override
    void saveStated(@NonNull Bundle outState) {
        if (stackEntries.size() == 0) {
            return;
        }
        ViewFlowState[] states = new ViewFlowState[stackEntries.size()];
        int count = stackEntries.size();
        for (int i = 0; i < count; ++i) {
            StackEntry stackEntry = stackEntries.get(i);
            ViewFlowState state = new ViewFlowState(stackEntry.tag,
                    stackEntry.viewFlow.getArguments(), stackEntry.viewFlow.getClass().getName());
            View view = stackEntry.viewFlow.getView();
            if (null != view) {
                SparseArray<Parcelable> viewHierarchy = new SparseArray<>();
                view.saveHierarchyState(viewHierarchy);
                state.saveViewHierarchy(viewHierarchy);
            }
            stackEntry.viewFlow.onSaveInstanceState(outState);
            states[i] = state;
        }
        ViewFlowManagerState viewFlowManagerState = new ViewFlowManagerState(states);
        outState.putParcelable(EXTRA_VIEWFLOW_MANAGER_STATE, viewFlowManagerState);
    }

    @Override
    void restoreStated(@NonNull Bundle savedInstanceState) {
        ViewFlowManagerState viewFlowManagerState = savedInstanceState.getParcelable(EXTRA_VIEWFLOW_MANAGER_STATE);
        if (null != viewFlowManagerState) {
            stackEntries = new ArrayList<>();
            for (ViewFlowState state : viewFlowManagerState.states) {
                ViewFlow viewFlow = state.instantiate(activity);
                viewFlow.onAttach(activity);
                StackEntry stackEntry = new StackEntry(viewFlow, state.tag);
                stackEntry.viewHierarchy = state.getViewHierarchy();
                stackEntries.add(stackEntry);
            }
        }
    }

    @Override
    void dispatchOnCreate(@Nullable Bundle savedInstanceState) {
        state = LifeCycleState.STATE_CREATE;
        this.savedInstanceState = savedInstanceState;
        if (stackEntries.size() == 0) {
            return;
        }
        StackEntry stackEntry = stackEntries.get(stackEntries.size() - 1);
        onCreate(stackEntry.viewFlow, savedInstanceState, stackEntry.viewHierarchy, null == savedInstanceState);
    }

    @Override
    void dispatchOnResume() {
        state = LifeCycleState.STATE_RESUME;
        if (stackEntries.size() == 0) {
            return;
        }
        StackEntry stackEntry = stackEntries.get(stackEntries.size() - 1);
        @LifeCycleState.State
        int state = stackEntry.viewFlow.state;
        switch (state) {
            case LifeCycleState.STATE_CREATE:
            case LifeCycleState.STATE_PAUSE:
            case LifeCycleState.STATE_STOP:
                onResume(stackEntry.viewFlow);
                break;
            default:
                break;
        }
    }

    @Override
    void dispatchOnPause() {
        state = LifeCycleState.STATE_PAUSE;
        if (stackEntries.size() == 0) {
            return;
        }
        StackEntry stackEntry = stackEntries.get(stackEntries.size() - 1);
        @LifeCycleState.State
        int state = stackEntry.viewFlow.state;
        switch (state) {
            case LifeCycleState.STATE_RESUME:
                onPause(stackEntry.viewFlow);
                break;
            default:
                break;
        }
    }

    @Override
    void dispatchOnStop() {
        state = LifeCycleState.STATE_STOP;
        if (stackEntries.size() == 0) {
            return;
        }
        StackEntry stackEntry = stackEntries.get(stackEntries.size() - 1);
        @LifeCycleState.State
        int state = stackEntry.viewFlow.state;
        switch (state) {
            case LifeCycleState.STATE_RESUME:
            case LifeCycleState.STATE_PAUSE:
                onStop(stackEntry.viewFlow);
                break;
            default:
                break;
        }
    }

    @Override
    void dispatchOnDestroy(Activity activity) {
        this.activity = null;
        state = LifeCycleState.STATE_DESTROY;
        for (StackEntry stackEntry : stackEntries) {
            moveToState(stackEntry.viewFlow,LifeCycleState.STATE_DESTROY,false);
        }
    }

    @Override
    void dispatchOnAttach(ViewFlowActivity activity) {
        this.activity = activity;
    }

    @Override
    void dispatchOnTrimMemory(int level) {
        for (StackEntry stackEntry : stackEntries) {
            stackEntry.viewFlow.onTrimMemory(level);
        }
    }

    @Override
    void dispatchOnBackPressed() {
        if (stackEntries.size() == 0) {
            return;
        }
        stackEntries.get(stackEntries.size() - 1).viewFlow.onBackPressed();
    }

    private void onCreate(ViewFlow viewFlow, Bundle savedInstanceState, SparseArray<Parcelable> viewHierarchy,boolean shouldAnim) {
        viewFlow.state = LifeCycleState.STATE_CREATE;
        ViewGroup parent = getDecorView();
        View view = viewFlow.onCreate(parent, savedInstanceState);
        if (null != view) {
            viewFlow.setView(view);
            if (null != viewHierarchy) {
                view.restoreHierarchyState(viewHierarchy);
            }
            changeView(viewFlow,null,shouldAnim);
        }
    }

    private void onResume(ViewFlow viewFlow) {
        viewFlow.state = LifeCycleState.STATE_RESUME;
        viewFlow.onResume();
    }

    private void onPause(ViewFlow viewFlow) {
        viewFlow.state = LifeCycleState.STATE_PAUSE;
        viewFlow.onPause();
    }

    private void onStop(ViewFlow viewFlow) {
        viewFlow.state = LifeCycleState.STATE_STOP;
        viewFlow.onStop();
    }

    private void onDestroy(ViewFlow viewFlow) {
        viewFlow.state = LifeCycleState.STATE_DESTROY;
        viewFlow.onDestroy();
    }

    private void moveToState(ViewFlow viewFlow, @LifeCycleState.State int state, boolean shouldAnim) {
        int viewFlowState = viewFlow.state;
        while (viewFlowState < state) {
            viewFlowState++;
            switch (viewFlowState) {
                case LifeCycleState.STATE_CREATE:
                    onCreate(viewFlow, savedInstanceState, null,shouldAnim);
                    break;
                case LifeCycleState.STATE_RESUME:
                    onResume(viewFlow);
                    break;
                case LifeCycleState.STATE_PAUSE:
                    onPause(viewFlow);
                    break;
                case LifeCycleState.STATE_STOP:
                    onStop(viewFlow);
                    break;
                case LifeCycleState.STATE_DESTROY:
                    onDestroy(viewFlow);
                    break;
                default:
                    break;
            }
        }
    }

    private void changeToState(ViewFlow viewFlow,@LifeCycleState.State int state) {
        switch (state){
            case LifeCycleState.STATE_RESUME:
                switch (viewFlow.state){
                    case LifeCycleState.STATE_PAUSE:
                    case LifeCycleState.STATE_STOP:
                        onResume(viewFlow);
                        break;
                    default:
                        moveToState(viewFlow, state, false);
                        break;
                }
                break;
            default:
                moveToState(viewFlow, state, false);
                break;
        }
    }

    private void changeView(ViewFlow enterFlow,ViewFlow exitFlow,boolean shouldAnim){
        ViewGroup parent = getDecorView();
        if(!shouldAnim){
            parent.addView(enterFlow.getView());
            return;
        }
        Animator enterAnimator = enterFlow.getEnterAnimator();
        if(enterAnimator != null){
            parent.addView(enterFlow.getView());
            enterAnimator.start();
        }else{
            parent.addView(enterFlow.getView());
        }
    }

}
