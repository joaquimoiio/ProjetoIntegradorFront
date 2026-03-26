package com.totalcross;

import com.totalcross.ui.Login;

import totalcross.sys.Settings;
import totalcross.ui.MainWindow;
public class App extends MainWindow {



	public App() {
        setUIStyle(Settings.MATERIAL_UI);
    }

	static {
		Settings.applicationId = "App";
		Settings.appVersion = "1.0.1";
		Settings.iosCFBundleIdentifier = "com.totalcross";
	}

    @Override
	public void initUI() {
		add(new Login(), LEFT, TOP, FILL, FILL);
	}


}

