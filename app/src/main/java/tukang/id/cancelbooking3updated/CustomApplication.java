package tukang.id.cancelbooking3updated;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/*** Created by Meidika on 9/29/2015.
 */
public class CustomApplication extends Application {

    private static boolean appIsInBackground;
    private static CustomApplication mInstance;

	private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
		mContext = this;
    }

    public static synchronized CustomApplication getInstance() {
        return mInstance;
    }

    public static boolean isAppIsInBackground() {
        return appIsInBackground;
    }

    public static void activityResumed(Activity activity) {
        appIsInBackground = false;
    }

	public static void activityPaused(Activity activity) {
		appIsInBackground = true;
	}

	public static Context getContext(){
		return mContext;
	}
}
