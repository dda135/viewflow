package fanjh.mine.viewflower;

import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
* @author fanjh
* @date 2018/1/19 16:29
* @description ViewFlow的存储节点数据结构
* @note
**/
public class StackEntry {
    ViewFlow viewFlow;
    String tag;
    SparseArray<Parcelable> viewHierarchy;

    public StackEntry(ViewFlow viewFlow, String tag) {
        this.viewFlow = viewFlow;
        this.tag = tag;
    }
}
