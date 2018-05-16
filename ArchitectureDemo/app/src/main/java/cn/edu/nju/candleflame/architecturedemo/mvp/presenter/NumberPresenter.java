package cn.edu.nju.candleflame.architecturedemo.mvp.presenter;

import android.graphics.Color;
import android.os.Handler;

import cn.edu.nju.candleflame.architecturedemo.mvp.model.NumberModel;
import cn.edu.nju.candleflame.architecturedemo.mvp.view.IMVPFragment;

public class NumberPresenter {

    private IMVPFragment mView;
    private NumberModel mNumberModel;

    public NumberPresenter(IMVPFragment view) {
        mView = view;
        mNumberModel = new NumberModel();
    }

    public void loadNumber() {
        final int num = mNumberModel.getNum();
        //接口请求前显示loading
        mView.showLoading();
        //这里模拟接口请求回调
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.showNumber(num);
                mView.changeColor(num % 2 == 0 ? Color.RED : Color.BLUE);
                mView.dismissLoading();
            }
        }, 1000);
    }

    public void PlusOne() {
        mNumberModel.Plus();
        loadNumber();
    }

    public void MinusOne() {
        mNumberModel.Minus();
        loadNumber();
    }

}
