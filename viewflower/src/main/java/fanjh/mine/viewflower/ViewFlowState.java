package fanjh.mine.viewflower;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import java.lang.reflect.InvocationTargetException;

/**
* @author fanjh
* @date 2018/1/23 9:35
* @description 用于存储ViewFlow的状态以及恢复
* @note
**/
public class ViewFlowState implements Parcelable{
    public static final String EXTRA_VIEW_HIERARCHY = "extra_view_hierarchy";
    String tag;
    Bundle arguments;
    Bundle mSavedState;
    String className;

    public ViewFlowState(String tag, Bundle arguments, String className) {
        this.tag = tag;
        this.arguments = arguments;
        this.className = className;
    }

    protected ViewFlowState(Parcel in) {
        tag = in.readString();
        arguments = in.readBundle(getClass().getClassLoader());
        mSavedState = in.readBundle(getClass().getClassLoader());
        className = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tag);
        dest.writeBundle(arguments);
        dest.writeBundle(mSavedState);
        dest.writeString(className);
    }

    public void saveViewHierarchy(SparseArray<Parcelable> viewHierarchy){
        if(null == viewHierarchy){
            return;
        }
        if(null == mSavedState){
            mSavedState = new Bundle();
        }
        mSavedState.putSparseParcelableArray(EXTRA_VIEW_HIERARCHY,viewHierarchy);
    }

    public SparseArray<Parcelable> getViewHierarchy(){
        if(null == mSavedState){
            return null;
        }
        return mSavedState.getSparseParcelableArray(EXTRA_VIEW_HIERARCHY);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ViewFlowState> CREATOR = new Creator<ViewFlowState>() {
        @Override
        public ViewFlowState createFromParcel(Parcel in) {
            return new ViewFlowState(in);
        }

        @Override
        public ViewFlowState[] newArray(int size) {
            return new ViewFlowState[size];
        }
    };

    public ViewFlow instantiate(Activity activity){
        try {
            Class cls = activity.getClassLoader().loadClass(className);
            ViewFlow viewFlow = (ViewFlow) cls.getConstructor().newInstance();
            viewFlow.setArguments(arguments);
            return viewFlow;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
