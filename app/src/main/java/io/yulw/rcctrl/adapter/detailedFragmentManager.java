package io.yulw.rcctrl.adapter;

import android.app.Activity;

import io.yulw.rcctrl.fragments.DetailedFragment;

/**
 * Created by yulw on 6/13/2015.
 */
class detailedFragmentManager {
    private static detailedFragmentManager mInst = null;

    public static detailedFragmentManager instance() {
        if (mInst == null)
            mInst = new detailedFragmentManager();
        return mInst;
    }

    public DetailedFragment<?> createFragment(Activity activity, SHORTCUTS type) {
        switch (type) {
            case SHORTCUT_VIDEO:
                return createVideoShortcut(activity);
            case SHORTCUT_SCENE:
                return createSceneShortcut(activity);
            case SHORTCUT_SYS:
                return createSysShortcut(activity);
            case SHORTCUT_LOG:
                return createLogShortcut(activity);
            default:
                break;
        }
        return null;
    }

    public DetailedFragment<?> createFragment(Activity activity, int position) {
        switch (position) {
            case 0:
                return detailedFragmentManager.instance().createFragment(activity, SHORTCUTS.SHORTCUT_VIDEO);
            case 1:
                return detailedFragmentManager.instance().createFragment(activity, SHORTCUTS.SHORTCUT_SCENE);
            case 2:
                return detailedFragmentManager.instance().createFragment(activity, SHORTCUTS.SHORTCUT_SYS);
            case 3:
                return detailedFragmentManager.instance().createFragment(activity, SHORTCUTS.SHORTCUT_LOG);
            default:
                break;
        }
        return null;
    }

    private DetailedFragment<?> createVideoShortcut(Activity activity) {
        DetailedFragment<rcVideoShortcut> detail = new DetailedFragment<rcVideoShortcut>();
        detail.setImpl(new rcVideoShortcut(activity));
        return detail;
    }

    private DetailedFragment<?> createSceneShortcut(Activity activity) {
        DetailedFragment<rcSceneShortcut> detail = new DetailedFragment<rcSceneShortcut>();
        detail.setImpl(new rcSceneShortcut(activity));
        return detail;
    }

    private DetailedFragment<?> createSysShortcut(Activity activity) {
        DetailedFragment<rcSysShortcut> detail = new DetailedFragment<rcSysShortcut>();
        detail.setImpl(new rcSysShortcut(activity));
        return detail;
    }

    private DetailedFragment<?> createLogShortcut(Activity activity) {
        DetailedFragment<rcLogShortcut> detail = new DetailedFragment<rcLogShortcut>();
        detail.setImpl(new rcLogShortcut(activity));
        return detail;
    }
}
