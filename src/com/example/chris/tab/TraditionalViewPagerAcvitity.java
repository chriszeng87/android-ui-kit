package com.example.chris.tab;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.chris.R;

public class TraditionalViewPagerAcvitity extends Activity
{

	/**
	 * ViewPager
	 */
	private ViewPager mViewPager;
	/**
	 * ViewPager��������
	 */
	private PagerAdapter mAdapter;
	private List<View> mViews;
	private LayoutInflater mInflater;
	
	private int currentIndex;

	/**
	 * �ײ��ĸ���ť
	 */
	private LinearLayout mTabBtnWeixin;
	private LinearLayout mTabBtnFrd;
	private LinearLayout mTabBtnAddress;
	private LinearLayout mTabBtnSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mInflater = LayoutInflater.from(this);
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		
		/**
		 * ��ʼ��View
		 */
		initView();
		
		mViewPager.setAdapter(mAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int position)
			{
				resetTabBtn();
				switch (position)
				{
				case 0:
					((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
							.setImageResource(R.drawable.tab_weixin_pressed);
					break;
				case 1:
					((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
							.setImageResource(R.drawable.tab_find_frd_pressed);
					break;
				case 2:
					((ImageButton) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_contact))
							.setImageResource(R.drawable.tab_address_pressed);
					break;
				case 3:
					((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
							.setImageResource(R.drawable.tab_settings_pressed);
					break;
				}

				currentIndex = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}
		});

	}

	protected void resetTabBtn()
	{
		((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
				.setImageResource(R.drawable.tab_weixin_normal);
		((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
				.setImageResource(R.drawable.tab_find_frd_normal);
		((ImageButton) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_contact))
				.setImageResource(R.drawable.tab_address_normal);
		((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
				.setImageResource(R.drawable.tab_settings_normal);
	}

	private void initView()
	{

		mTabBtnWeixin = (LinearLayout) findViewById(R.id.id_tab_bottom_weixin);
		mTabBtnFrd = (LinearLayout) findViewById(R.id.id_tab_bottom_friend);
		mTabBtnAddress = (LinearLayout) findViewById(R.id.id_tab_bottom_contact);
		mTabBtnSettings = (LinearLayout) findViewById(R.id.id_tab_bottom_setting);

		mViews = new ArrayList<View>();
		View first = mInflater.inflate(R.layout.main_tab_01, null);
		View second = mInflater.inflate(R.layout.main_tab_02, null);
		View third = mInflater.inflate(R.layout.main_tab_03, null);
		View fourth = mInflater.inflate(R.layout.main_tab_04, null);
		mViews.add(first);
		mViews.add(second);
		mViews.add(third);
		mViews.add(fourth);

		mAdapter = new PagerAdapter()
		{
			@Override
			public void destroyItem(ViewGroup container, int position, Object object)
			{
				container.removeView(mViews.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position)
			{
				View view = mViews.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == arg1;
			}

			@Override
			public int getCount()
			{
				return mViews.size();
			}
		};
	}

}
