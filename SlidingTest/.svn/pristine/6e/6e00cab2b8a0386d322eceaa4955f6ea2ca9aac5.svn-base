package com.slidingtest.widget;

import com.slidingtest.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TopBar extends LinearLayout implements OnClickListener {
    private TextView title;
    private Button btnRight;
    private ImageButton  btnleft;
    private HeaderButtons hbc = null;

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TopBar(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.titlebar, this);
        title = (TextView) findViewById(R.id.title);
        btnleft = (ImageButton) findViewById(R.id.btnTopLeft);
        btnleft.setOnClickListener(this);
    }

    public void setTitleText(String text) {
        title.setText(text);
    }


    public ImageButton getLeftButton() {
        return btnleft;
    }

    public static interface HeaderButtons {
        public void onLeftButtonClicked();
        public void initHeader();
        
    }

    public void setButtonsClickCallback(HeaderButtons _hbc) {
        hbc = _hbc;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        hbc.onLeftButtonClicked();
    }
}