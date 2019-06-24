package com.chenyingjie.pickerview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


import com.chenyingjie.pickerview.bean.UnitBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen.yingjie on 2019/6/22
 * description
 */
public class UnitPicker extends WheelPicker<String> {

    public UnitPicker(Context context) {
        this(context, null);
    }

    public UnitPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnitPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        data = new ArrayList<>();
        setOnWheelChangeListener(new OnWheelChangeListener<String>() {

            @Override
            public void onWheelSelected(String item, int position) {
                if (mOnItemSelectedListener != null) {
                    mOnItemSelectedListener.onItemSelected(data.get(position), position);
                }
            }
        });
    }

    private List<UnitBean> data;

    public void setData(List<UnitBean> unitBeans) {
        if (unitBeans != null) {
            data.addAll(unitBeans);
            ArrayList<String> list = new ArrayList<>();
            for (UnitBean unitBean : unitBeans) {
                list.add(unitBean.unit_cn);
            }
            setDataList(list);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(UnitBean unitBean, int position);
    }

    private OnItemSelectedListener mOnItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public UnitBean setUnit(int unit) {
        for (UnitBean bean : data) {
            if (bean.unit == unit) {
                setCurrentPosition(data.indexOf(bean), false);
                return bean;
            }
        }
        return null;
    }

    public UnitBean getCurrentUnit() {
        try {
            return data.get(getCurrentPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
