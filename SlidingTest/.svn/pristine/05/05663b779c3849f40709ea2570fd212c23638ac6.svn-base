package com.slidingtest;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.slidingtest.constant.CommonFields;
import com.slidingtest.utils.Utils;

public class SettingsActivity extends Fragment {
//    private ToggleButton switcher = null;
//    private EditText password = null;
//    private Button savePwd = null;
    private CheckedTextView checkedTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        checkedTextView = (CheckedTextView) v.findViewById(R.id.checkOption);
        checkedTextView.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivityForResult(new Intent(SettingsActivity.this.getActivity(), SetPasswordActivity.class), 1);
            }
            
        });
        SharedPreferences sp = SettingsActivity.this.getActivity().getSharedPreferences(CommonFields.CONFIGURETION,
              Context.MODE_PRIVATE);
        if(sp.getBoolean(CommonFields.SWITCHER_STATUS, false)){
            checkedTextView.setCheckMarkDrawable(R.drawable.on);
        }
//        switcher = (ToggleButton) v.findViewById(R.id.switcher);
//        password = (EditText) v.findViewById(R.id.password);
//        savePwd = (Button) v.findViewById(R.id.savePwd);
//        
//        
//        final SharedPreferences sp = SettingsActivity.this.getActivity().getSharedPreferences(CommonFields.CONFIGURETION,
//                Context.MODE_PRIVATE);
//        if(sp.getBoolean(CommonFields.SWITCHER_STATUS, false)){
//            switcher.setChecked(true);
//            password.setVisibility(View.VISIBLE);
//            password.setText(sp.getString(CommonFields.KEY, ""));
//            password.setSelection(sp.getString(CommonFields.KEY, "").length());
//            savePwd.setVisibility(View.VISIBLE);
//        }
//
//        switcher.setOnCheckedChangeListener(new OnCheckedChangeListener(){
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // TODO Auto-generated method stub
//                if(isChecked){
//                    password.setVisibility(View.VISIBLE);
//                    password.setText(sp.getString(CommonFields.KEY, ""));
//                    savePwd.setVisibility(View.VISIBLE);
//                }else{
//                    password.setVisibility(View.GONE);
//                    savePwd.setVisibility(View.GONE);
//                }
//                Editor sharedata = sp.edit();
//                sharedata.putBoolean(CommonFields.SWITCHER_STATUS, isChecked);
//                sharedata.commit();
//            }
//            
//        });
//        
//        savePwd.setOnClickListener(new OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                if("".equals(password.getText().toString())){
//                    Toast.makeText(v.getContext(), "密码不能为空!!!", Toast.LENGTH_SHORT).show();
//                }else{
//                    Editor sharedata = sp.edit();
//                    sharedata.putString(CommonFields.KEY, password.getText().toString());
//                    if(sharedata.commit()){
//                        AlertDialog.Builder builder = new Builder(SettingsActivity.this.getActivity());
//                        builder.setTitle("状态");
//                        builder.setMessage("设置成功!");
//                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // TODO Auto-generated method stub
//                                Utils.changeTitleText(getActivity());
//                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DoneListActivity()).commit();
//                            }
//                        });
//                        builder.create().show();
//                    }
//                }
//            }
//            
//        });
        
        return v;
    }
    
    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        Utils.changeTitleText(getActivity());
        super.onDetach();
    }

}
