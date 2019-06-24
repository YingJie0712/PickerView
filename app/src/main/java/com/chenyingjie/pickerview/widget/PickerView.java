package com.chenyingjie.pickerview.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


import com.chenyingjie.pickerview.R;
import com.chenyingjie.pickerview.bean.UnitBean;
import com.chenyingjie.pickerview.util.NumUtils;
import com.chenyingjie.pickerview.util.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chen.yingjie on 2019/6/22
 * description
 */
public class PickerView extends LinearLayout {

    @BindView(R.id.numPicker100)
    NumPicker numPicker100;
    @BindView(R.id.numPicker10)
    NumPicker numPicker10;
    @BindView(R.id.numPicker1)
    NumPicker numPicker1;
    @BindView(R.id.numPicker_1)
    NumPicker numPicker_1;
    @BindView(R.id.unitPicker)
    UnitPicker unitPicker;

    /**
     * 百位数
     */
    private int hundredBit;
    /**
     * 十位数
     */
    private int tenBit;
    /**
     * 个位数
     */
    private int bit;
    /**
     * 小数位数
     */
    private int decimalBit;

    /**
     * 总数
     */
    private double amount;

    /**
     * NumPicker显示状态
     */
    private boolean num100Visiable;
    private boolean num10Visiable;
    private boolean num1Visiable;
    private boolean num_1Visiable;

    public PickerView(Context context) {
        this(context, null);
    }

    public PickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_picker_view, this, true);
        ButterKnife.bind(this);

        setTextSize(ScreenUtils.dip2px(context, 26f));
        setTextColor(getResources().getColor(R.color.gray));
        setSelectedItemTextSize(ScreenUtils.dip2px(context, 30f));
        setCyclic(true);
        setTextGradual(true);
        setShowCurtain(true);
        setShowCurtainBorder(false);
        setCurtainColor(getResources().getColor(R.color.c1f1f23));

        numPicker100.setOnItemSelectedListner(new NumPicker.OnItemSelectedListner() {

            @Override
            public void onItemSelected(String item, int position) {
                if (mOnItemSelectedListener != null) {
                    if (NumUtils.isDigit(item)) {
                        hundredBit = Integer.valueOf(item);
                    }
                    toAmount();
                    UnitBean bean = unitPicker.getCurrentUnit();
                    mOnItemSelectedListener.onItemSelected(bean.unit, bean.unit_cn, amount);
                }
            }
        });
        numPicker10.setOnItemSelectedListner(new NumPicker.OnItemSelectedListner() {
            @Override
            public void onItemSelected(String item, int position) {
                if (mOnItemSelectedListener != null) {
                    if (NumUtils.isDigit(item)) {
                        tenBit = Integer.valueOf(item);
                    }
                    toAmount();
                    UnitBean bean = unitPicker.getCurrentUnit();
                    mOnItemSelectedListener.onItemSelected(bean.unit, bean.unit_cn, amount);
                }
            }
        });
        numPicker1.setOnItemSelectedListner(new NumPicker.OnItemSelectedListner() {
            @Override
            public void onItemSelected(String item, int position) {
                if (mOnItemSelectedListener != null) {
                    if (NumUtils.isDigit(item)) {
                        bit = Integer.valueOf(item);
                    }
                    toAmount();
                    UnitBean bean = unitPicker.getCurrentUnit();
                    mOnItemSelectedListener.onItemSelected(bean.unit, bean.unit_cn, amount);
                }
            }
        });
        numPicker_1.setOnItemSelectedListner(new NumPicker.OnItemSelectedListner() {
            @Override
            public void onItemSelected(String item, int position) {
                if (mOnItemSelectedListener != null) {
                    if (NumUtils.isDigit(item)) {
                        decimalBit = Integer.valueOf(item);
                    }
                    toAmount();
                    UnitBean bean = unitPicker.getCurrentUnit();
                    mOnItemSelectedListener.onItemSelected(bean.unit, bean.unit_cn, amount);
                }
            }
        });

        unitPicker.setOnItemSelectedListener(new UnitPicker.OnItemSelectedListener() {

            @Override
            public void onItemSelected(UnitBean item, int position) {
                setUnitBean(item);
                resetNumPicker(item);
                if (mOnItemSelectedListener != null) {
                    toAmount();
                    UnitBean bean = unitPicker.getCurrentUnit();
                    mOnItemSelectedListener.onItemSelected(bean.unit, bean.unit_cn, amount);
                }
            }
        });

    }

    public void setData(List<UnitBean> data) {
        unitPicker.setData(data);
        unitPicker.setCurrentPosition(0);
        UnitBean unitBean = data.get(0);
        setUnitBean(unitBean);
        resetNumPicker(unitBean);
        if (mOnItemSelectedListener != null) {
            toAmount();
            mOnItemSelectedListener.onItemSelected(unitPicker.getCurrentUnit().unit, unitPicker.getCurrentUnit().unit_cn, amount);
        }
    }

    /**
     * 回显
     * @param number 已选择的数量
     * @param unit  已选择的单位
     */
    public void setNumberAndUnit(double number, int unit) {
        int bit100, bit10, bit1, bit_1;
        // 取百位数
        bit100 = (int) (number / 100 % 10);
        // 取十位数
        bit10 = (int) (number / 10 % 10);
        // 取个位数
        bit1 = (int) (number % 10);
        // 取小数位数
        bit_1 = (int) (number * 10 % 10);
        UnitBean b = unitPicker.setUnit(unit);
        if (b != null) {
            if (num100Visiable)
                numPicker100.setNumber(String.valueOf(bit100));
            if (num10Visiable)
                numPicker10.setNumber(String.valueOf(bit10));
            if (num1Visiable)
                if (unit == 8 || unit == 7) { // 适量、少许
                    numPicker1.setNumber(b.unit_cn);
                } else {
                    numPicker1.setNumber(String.valueOf(bit1));
                }
            if (num_1Visiable)
                numPicker_1.setNumber(String.valueOf(bit_1));
        }
    }

    /**
     * 转换结果
     */
    private void toAmount() {
        String str100 = String.valueOf(num100Visiable ? hundredBit : "");
        String str10 = String.valueOf(num10Visiable ? tenBit : "");
        String str1 = String.valueOf(num1Visiable ? bit : "");
        String str_1 = String.valueOf(num_1Visiable ? "." + decimalBit : "");
        String format = String.format("%1$s%2$s%3$s%4$s", str100, str10, str1, str_1);
        if (TextUtils.isEmpty(format)) format = "0";
        amount = Double.valueOf(format);
    }

    private void setUnitBean(UnitBean item) {
        numPicker100.setData(item);
        numPicker10.setData(item);
        numPicker1.setData(item);
        numPicker_1.setData(item);
    }

    /**
     * 设置滚轮个数
     * @param item
     */
    private void resetNumPicker(UnitBean item) {
        if (null != item) {
            int unit = item.unit;
            switch (unit) {
                case 1:
                case 2:
                case 3:
                    setNumPickerVisiable(false, true, true, true, true);
                    break;
                case 4:
                case 5:
                case 6:
                    setNumPickerVisiable(true, true, true, false, false);
                    break;
                case 7:
                case 8:
                    setNumPickerVisiable(false, false, true, false, false);
                    break;
                case 9:
                    setNumPickerVisiable(true, true, true, true, true);
                    break;
            }
        }
    }

    private void setNumPickerVisiable(boolean num100Visiable, boolean num10Visiable, boolean num1Visiable, boolean needPoint, boolean num_1Visiable) {
        this.num100Visiable = num100Visiable;
        this.num10Visiable = num10Visiable;
        this.num1Visiable = num1Visiable;
        this.num_1Visiable = num_1Visiable;

        numPicker100.setVisibility(num100Visiable ? VISIBLE : GONE);
        numPicker10.setVisibility(num10Visiable ? VISIBLE : GONE);
        numPicker1.setVisibility(num1Visiable ? VISIBLE : GONE);
        numPicker1.setPoint(needPoint);
        numPicker_1.setVisibility(num_1Visiable ? VISIBLE : GONE);
    }

    public void setTextColor(@ColorInt int textColor) {
        numPicker100.setTextColor(textColor);
        numPicker10.setTextColor(textColor);
        numPicker1.setTextColor(textColor);
        numPicker_1.setTextColor(textColor);
        unitPicker.setTextColor(textColor);
    }

    public void setTextSize(int textSize) {
        numPicker100.setTextSize(textSize);
        numPicker10.setTextSize(textSize);
        numPicker1.setTextSize(textSize);
        numPicker_1.setTextSize(textSize);
        unitPicker.setTextSize(textSize);
    }

    public void setSelectedItemTextSize(int selectedItemTextSize) {
        numPicker100.setSelectedItemTextSize(selectedItemTextSize);
        numPicker10.setSelectedItemTextSize(selectedItemTextSize);
        numPicker1.setSelectedItemTextSize(selectedItemTextSize);
        numPicker_1.setSelectedItemTextSize(selectedItemTextSize);
        unitPicker.setSelectedItemTextSize(selectedItemTextSize);
    }

    /**
     * 好像不起作用，只能在布局中设置。
     *
     * @param selectedItemTextColor
     */
    public void setSelectedItemTextColor(int selectedItemTextColor) {
        numPicker100.setSelectedItemTextColor(selectedItemTextColor);
        numPicker10.setSelectedItemTextColor(selectedItemTextColor);
        numPicker1.setSelectedItemTextColor(selectedItemTextColor);
        numPicker_1.setSelectedItemTextColor(selectedItemTextColor);
        unitPicker.setSelectedItemTextColor(selectedItemTextColor);
    }

    public void setTextGradual(boolean textGradual) {
        numPicker100.setTextGradual(textGradual);
        numPicker10.setTextGradual(textGradual);
        numPicker1.setTextGradual(textGradual);
        numPicker_1.setTextGradual(textGradual);
        unitPicker.setTextGradual(textGradual);
    }

    public void setCyclic(boolean cyclic) {
        numPicker100.setCyclic(cyclic);
        numPicker10.setCyclic(cyclic);
        numPicker1.setCyclic(cyclic);
        numPicker_1.setCyclic(cyclic);
        unitPicker.setCyclic(cyclic);
    }

    /**
     * 设置中心Item是否有幕布遮盖
     * set the center item curtain cover
     *
     * @param showCurtain 是否有幕布
     */
    public void setShowCurtain(boolean showCurtain) {
        numPicker100.setShowCurtain(showCurtain);
        numPicker10.setShowCurtain(showCurtain);
        numPicker1.setShowCurtain(showCurtain);
        numPicker_1.setShowCurtain(showCurtain);
        unitPicker.setShowCurtain(showCurtain);
    }

    public void setShowCurtainBorder(boolean showCurtainBorder) {
        numPicker100.setShowCurtainBorder(showCurtainBorder);
        numPicker10.setShowCurtainBorder(showCurtainBorder);
        numPicker1.setShowCurtainBorder(showCurtainBorder);
        numPicker_1.setShowCurtainBorder(showCurtainBorder);
        unitPicker.setShowCurtainBorder(showCurtainBorder);
    }

    /**
     * 设置幕布颜色
     * set curtain color
     *
     * @param curtainColor 幕布颜色
     */
    public void setCurtainColor(@ColorInt int curtainColor) {
        numPicker100.setCurtainColor(curtainColor);
        numPicker10.setCurtainColor(curtainColor);
        numPicker1.setCurtainColor(curtainColor);
        numPicker_1.setCurtainColor(curtainColor);
        unitPicker.setCurtainColor(curtainColor);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int unit, String unit_cn, double amount);
    }

    private OnItemSelectedListener mOnItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }
}
