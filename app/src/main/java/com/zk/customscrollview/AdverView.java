package com.zk.customscrollview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;


/*
 * 广告轮播类
 * */
public class AdverView implements OnPageChangeListener {
	private ViewPager vp;
	private Context ctx;

	private RelativeLayout relat;
	private List<View> list=new ArrayList<View>();//vp适配器的数据源
	private int []arr={R.mipmap.introduce_01,R.mipmap.introduce_02,R.mipmap.introduce_03,R.mipmap.introduce_04};
	private boolean touchFlag=false;//触摸锁
	private boolean threadFlag=true;//控制发送消息的锁
	View [] views=new View[arr.length];
	private VpAdapter adapter;
	private int index=0;
	public AdverView(Context ctx) {
		this.ctx=ctx;
		initList();
		initView();
		TimeThread thread=new TimeThread();
		thread.start();
	}
	//获取当前view的方法
	public View getView(){
		return relat;
	}
	private void initView() {
		relat=new RelativeLayout(ctx);
		vp=new ViewPager(ctx);
		adapter=new VpAdapter();
		vp.setAdapter(adapter);
		vp.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
		relat.addView(vp);
		vp.setOnPageChangeListener(this);
	}
	//初始化数据源
	private void initList() {
		for(int i=0;i<arr.length;i++){
			ImageView img=new ImageView(ctx);
			img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			img.setScaleType(ScaleType.FIT_XY);
			img.setImageResource(arr[i]);
			list.add(img);
			views[i]=img;
		}
	}
	private Handler hand=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==0){
				if(touchFlag){
					return;
				}
				if(!threadFlag){
					return;
				}
				index++;
				vp.setCurrentItem(index);
			}
		}
	};
	private class VpAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager)container).removeView(list.get(position%views.length));
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			((ViewPager)container).addView(list.get(position%views.length));
			//将要显示的View返回出去
			return list.get(position%views.length);
		}
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		if(arg0==ViewPager.SCROLL_STATE_IDLE){
			touchFlag=false;//人为滑动结束之后改为false,继续启动
		}else{
			touchFlag=true;//人为滑动时，停止自动轮播
		}
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
	@Override
	public void onPageSelected(int arg0) {
		//
		index=arg0;
	}
	private class TimeThread extends Thread{
		@Override
		public void run() {
			while(threadFlag){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hand.sendEmptyMessage(0);
			}
		}
	}
}
