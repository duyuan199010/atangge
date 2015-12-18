package com.strod.yssl.clientcore.service;

import com.strod.yssl.bean.personal.User;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author lying
 */
public class LoginListenerMgr {
    private static LoginListenerMgr sInstance = new LoginListenerMgr();

    private Vector<OnLoginListener> loginListeners;

    public static LoginListenerMgr getInstance() {
        return sInstance;
    }

    private LoginListenerMgr() {
        loginListeners = new Vector<OnLoginListener>();
    }

    public synchronized void addListener(OnLoginListener onLoginListener) {
        if (!loginListeners.contains(onLoginListener)) {
            loginListeners.add(onLoginListener);
        }
    }

    public synchronized void removeListener(OnLoginListener onLoginListener) {
        if (loginListeners.contains(onLoginListener)) {
            loginListeners.remove(onLoginListener);
        }
    }

    public void notifyLoginChanged(User user) {
        Enumeration<OnLoginListener> enumo = loginListeners.elements();
        while (enumo.hasMoreElements()) {
            enumo.nextElement().onLogin(user);
        }
    }
}
