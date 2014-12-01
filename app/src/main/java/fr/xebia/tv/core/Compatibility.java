package fr.xebia.tv.core;

import android.os.Build;
import android.view.ViewTreeObserver;

public class Compatibility {

    private Compatibility() {
    }

    public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            viewTreeObserver.removeOnGlobalLayoutListener(listener);
        } else {
            viewTreeObserver.removeGlobalOnLayoutListener(listener);
        }
    }
}
