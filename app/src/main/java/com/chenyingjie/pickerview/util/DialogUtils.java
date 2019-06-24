package com.chenyingjie.pickerview.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.chenyingjie.pickerview.R;
import com.chenyingjie.pickerview.bean.UnitBean;
import com.chenyingjie.pickerview.widget.PickerView;

import java.util.List;

/**
 * Created by chen.yingjie on 2019/6/21
 * description
 */
public class DialogUtils {

    private static int tmpUnit;
    private static String tmpUnit_cn;
    private static double tmpAmount;

    public static void showPicker(Context context, List<UnitBean> data, final double amount, final int unit, final OnPickedListener onPickedListener) {
        final Dialog dialog = new Dialog(context, R.style.DialogCommonStyle);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);// 位置
        window.setContentView(R.layout.layout_picker_dialog);

        TextView tvCancel = window.findViewById(R.id.tvCancel);
        TextView tvOK = window.findViewById(R.id.tvOK);
        PickerView pickerView = window.findViewById(R.id.pickerView);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPickedListener != null) {
                    onPickedListener.onPicked(tmpUnit, tmpUnit_cn, tmpAmount);
                }
                dialog.dismiss();
            }
        });
        pickerView.setData(data);
        pickerView.setNumberAndUnit(amount, unit);
        pickerView.setOnItemSelectedListener(new PickerView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int unit, String unit_cn, double amount) {
                tmpUnit = unit;
                tmpUnit_cn = unit_cn;
                tmpAmount = amount;
            }
        });

        dialog.setCanceledOnTouchOutside(true);// 触摸外面关闭dialog
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ScreenUtils.getScreenWidth(context);// dialog宽等于屏幕宽
        lp.height = ScreenUtils.getScreenHeight(context) * 1/2;// dialog高为屏幕高一半
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.color.black);// dialog背景
        window.setWindowAnimations(R.style.AnimBottom);
        dialog.show();
    }

    public interface OnPickedListener {
        void onPicked(int unit, String unit_cn, double amount);
    }
}
