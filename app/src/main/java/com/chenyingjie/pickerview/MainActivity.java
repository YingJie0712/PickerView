package com.chenyingjie.pickerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chenyingjie.pickerview.bean.UnitBean;
import com.chenyingjie.pickerview.util.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn21)
    Button btn21;
    @BindView(R.id.tvResult)
    TextView tvResult;

    private List<UnitBean> list = new ArrayList<>();
    private String[] unit_cns = new String[]{"克", "两", "个", "勺", "块", "条", "少许", "适量", "毫升",};
    private UnitBean unitBean;
    private int mUnit;
    private String mUnit_cn;
    private double mAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        for (int i = 0; i < unit_cns.length; i++) {
            unitBean = new UnitBean();
            unitBean.unit = i+1;
            unitBean.unit_cn = unit_cns[i];
            list.add(unitBean);
        }

    }

    @OnClick({R.id.btn21})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn21:
                DialogUtils.showPicker(this, list, mAmount, mUnit, new DialogUtils.OnPickedListener() {
                    @Override
                    public void onPicked(int unit, String unit_cn, double amount) {
                        mUnit = unit;
                        mUnit_cn = unit_cn;
                        mAmount = amount;
                        tvResult.setText(String.format("%1$s%2$s", mAmount, mUnit_cn));
                    }
                });
                break;
        }
    }
}
