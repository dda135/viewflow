package fanjh.mine.viewflow;

import fanjh.mine.viewflower.LauncherMode;
import fanjh.mine.viewflower.ViewFlow;

public interface JumpController {
    void jump(String tag, ViewFlow viewFlow);
    void jump(String tag, ViewFlow viewFlow, @LauncherMode.Mode int mode);
}