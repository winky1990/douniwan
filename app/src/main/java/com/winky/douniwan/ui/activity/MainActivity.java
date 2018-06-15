package com.winky.douniwan.ui.activity;

import android.os.Bundle;

import com.winky.douniwan.R;
import com.winky.expand.basics.BaseSkinActivity;

import androidx.navigation.Navigation;

public class MainActivity extends BaseSkinActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(getActivity(), R.id.host_fragment).navigateUp();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
