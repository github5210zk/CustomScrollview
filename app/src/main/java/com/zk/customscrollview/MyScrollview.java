package com.zk.customscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.zk.customscrollview.MyAdapter;

/**
 * 自定义的scrollview
 * */
public class MyScrollview extends ScrollView{
	private LinearLayout linear;
	private LinearLayout topView;
	private ListView lv;
	public MyScrollview(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	private void initView() {
		//动态设置控件参数
		linear=new LinearLayout(getContext());
		//获取控件的参数：控件在父容器中所要占的位置的参数
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		linear.setLayoutParams(lp);
		//动态指定线方向
		linear.setOrientation(LinearLayout.VERTICAL);
		//将linear添加到自定义srcollview中
		addView(linear);
		//!!!!!!开始往linear添加内容
		topView=new LinearLayout(getContext());
		//给topView设置属性，设置其在父容器linear中所占的位置
		topView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		linear.addView(topView);

		lv=new ListView(getContext());
		LinearLayout.LayoutParams lvlp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		lvlp.topMargin=20; //上边距20
		lv.setLayoutParams(lvlp);
		//取消焦点，在最顶部显示
		lv.setFocusable(false);
		linear.addView(lv);
	}
	//给lv设置适配器的方法
	public void setAdapter(MyAdapter adapter){
		lv.setAdapter(adapter);
		measureLv();
	}
	public void setTop(View v){
		v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		topView.addView(v);
	}

	/**
	 * 测量 解决Scrollview嵌套listview的问题
	 */
	private void measureLv(){
		ListAdapter adapter=lv.getAdapter();
		int height=0;
		for(int i=0;i<adapter.getCount();i++){
			View item=adapter.getView(i, null, null);
			item.measure(0, 0);
			height+=item.getMeasuredHeight();
		}
		height=height+lv.getDividerHeight()*(adapter.getCount()-1)+lv.getPaddingTop()+lv.getPaddingBottom();
		LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) lv.getLayoutParams();
		lp.height=height;
		lv.setLayoutParams(lp);
		requestLayout();
	}
}
