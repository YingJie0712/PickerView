package com.chenyingjie.pickerview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


import com.chenyingjie.pickerview.R;
import com.chenyingjie.pickerview.bean.UnitBean;
import com.chenyingjie.pickerview.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen.yingjie on 2019/6/22
 * description
 */
public class NumPicker extends WheelPicker<String> {

    private OnItemSelectedListner onItemSelectedListner;
    private List<String> intList = new ArrayList<>();

    public NumPicker(Context context) {
        this(context, null);
    }

    public NumPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnWheelChangeListener(new OnWheelChangeListener<String>() {
            @Override
            public void onWheelSelected(String item, int position) {
                // item不一定为数字，这里使用position从本类集合获取结果即可。
                String integer = intList.get(position);
                if (onItemSelectedListner != null) {
                    onItemSelectedListner.onItemSelected(integer, position);
                }
            }
        });
    }

    public List<String> buildIntList() {
        List<String> iList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {// 0-9
            iList.add(String.valueOf(i));
        }
        return iList;
    }

    /**
     * 设置数据
     */
    public void setData(UnitBean item) {
        if (null == item) return;
        List<String> list = new ArrayList<>();
        intList.clear();
        int unit = item.unit;
        if (unit == 8 || unit == 7) {// 适量、少许
            list.add(item.unit_cn);
            intList.add(item.unit_cn);
        } else {
            for (int i = 0; i < 10; i++) {// 0-9
                intList.add(String.valueOf(i));
                list.add(String.valueOf(i));
            }
        }
        // 给滚轮设置数据集，以便滚轮显示数据。
        setDataList(list);
        setCurrentPosition(0, false);
    }

    /**
     * 添加小数点
     */
    public void setPoint(boolean need) {
        setIndicatorText(need ? "." : null);
        setIndicatorTextColor(getResources().getColor(R.color.white));
        setIndicatorTextSize(ScreenUtils.dip2px(getContext(), 30f));
    }

    /**
     * 回显
     * @param number
     */
    public void setNumber(String number) {
        intList.clear();
        intList = buildIntList();
        setCurrentPosition(intList.indexOf(number), false);
    }

    public interface OnItemSelectedListner {
        void onItemSelected(String item, int position);
    }

    public void setOnItemSelectedListner(OnItemSelectedListner onItemSelectedListner) {
        this.onItemSelectedListner = onItemSelectedListner;
    }
}
