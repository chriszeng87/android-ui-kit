package com.example.chris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chris.pulltorefreshtest.PullToRefreshActivity;
import com.example.chris.tab.FragmentMainActivity;
import com.example.chris.tab.TraditionalViewPagerAcvitity;

public class WelcomeActivity extends Activity
{
	private ListView mListView;
	private ArrayAdapter<String> mAdapter;
	private List<String> mData = new ArrayList<String>(Arrays.asList("Pull To Refresh","Traditional ViewPager" , "FragmentManager & Fragment"));

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		mListView = (ListView) findViewById(R.id.id_listview);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = null;
				switch (position)
				{
				case 0:
					intent = new Intent(WelcomeActivity.this, PullToRefreshActivity.class);
					break;
				case 1:
					intent = new Intent(WelcomeActivity.this, TraditionalViewPagerAcvitity.class);
					break;
				case 2:
					intent = new Intent(WelcomeActivity.this, FragmentMainActivity.class);
					break;

				default:
					break;
				}
				startActivity(intent);
			}
		});

	}

}
