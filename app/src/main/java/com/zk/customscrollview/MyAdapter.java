package com.zk.customscrollview;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/***
 * ListView适配器
 */
public class MyAdapter extends BaseAdapter{
	private List<String> list;
	private Context ctx;
	public MyAdapter(Context ctx,List<String> list) {
		this.ctx=ctx;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=View.inflate(ctx, R.layout.item_lv, null);
			holder.tv=(TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv.setText(list.get(position));
		return convertView;
	}
	private class ViewHolder{
		private TextView tv;
	}
}
