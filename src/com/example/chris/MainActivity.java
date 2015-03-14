package com.example.chris;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity
{
	MainView mainView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mainView = new MainView(this);
		setContentView(mainView);

	}

}
