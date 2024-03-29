package com.strod.yssl.clientcore.service;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author lying
 */
public class SwitchThemeListenerMgr {
    private static SwitchThemeListenerMgr sInstance = new SwitchThemeListenerMgr();

    private Vector<OnSwitchThemeListener> switchThemeListeners;

    public static SwitchThemeListenerMgr getInstance() {
        return sInstance;
    }

    private SwitchThemeListenerMgr() {
        switchThemeListeners = new Vector<OnSwitchThemeListener>();
    }

    public synchronized void addListener(OnSwitchThemeListener onSwitchThemeListener) {
        if (!switchThemeListeners.contains(onSwitchThemeListener)) {
            switchThemeListeners.add(onSwitchThemeListener);
        }
    }

    public synchronized void removeListener(OnSwitchThemeListener onSwitchThemeListener) {
        if (switchThemeListeners.contains(onSwitchThemeListener)) {
            switchThemeListeners.remove(onSwitchThemeListener);
        }
    }

    public void notifySwitchTheme(boolean isNight) {
        Enumeration<OnSwitchThemeListener> enumo = switchThemeListeners.elements();
        while (enumo.hasMoreElements()) {
            enumo.nextElement().switchTheme(isNight);
        }
    }
}
