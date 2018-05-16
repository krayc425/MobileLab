package cn.edu.nju.candleflame.architecturedemo.mvp.view;

public interface IMVPFragment {

    void showNumber(int num);

    void changeColor(int color);

    void showLoading();

    void dismissLoading();
}
