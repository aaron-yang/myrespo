package com.slidingtest;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class AboutFragment extends Fragment {
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    RelativeLayout v = new RelativeLayout(getActivity());
        v.setBackgroundColor(Color.BLUE);        
        return v;
	}
}
