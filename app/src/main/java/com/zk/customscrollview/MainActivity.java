package com.zk.customscrollview;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private List<String> list = new ArrayList<>();
    private MyAdapter    adapter;
    private MyScrollview mSC;
    private View         topView;
    private LinearLayout mLin;

    //private ImageView img;
    private AdverView adver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
        initView();
    }

    private void initView() {
        mSC = (MyScrollview) findViewById(R.id.mSC);
        topView = View.inflate(this, R.layout.top_view, null);
        mLin = (LinearLayout) topView.findViewById(R.id.mLin);
        adver = new AdverView(this);
        mLin.addView(adver.getView());
        mSC.setTop(topView);

        adapter = new MyAdapter(this, list);
        mSC.setAdapter(adapter);

    }

    private void initList() {
        for (int i = 0; i < 50; i++) {
            list.add("第" + i + "条数据");
        }
    }


}
