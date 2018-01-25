package fanjh.mine.viewflower;

import android.os.Parcel;
import android.os.Parcelable;

/**
* @author fanjh
* @date 2018/1/23 10:16
* @description 用于ViewFlow的整体状态保存和恢复
* @note
**/
public class ViewFlowManagerState implements Parcelable{
    ViewFlowState []states;

    public ViewFlowManagerState(ViewFlowState[] states) {
        this.states = states;
    }

    protected ViewFlowManagerState(Parcel in) {
        states = in.createTypedArray(ViewFlowState.CREATOR);
    }

    public static final Creator<ViewFlowManagerState> CREATOR = new Creator<ViewFlowManagerState>() {
        @Override
        public ViewFlowManagerState createFromParcel(Parcel in) {
            return new ViewFlowManagerState(in);
        }

        @Override
        public ViewFlowManagerState[] newArray(int size) {
            return new ViewFlowManagerState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(states, flags);
    }
}
