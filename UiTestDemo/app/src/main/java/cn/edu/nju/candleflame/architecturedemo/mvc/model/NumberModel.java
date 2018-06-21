package cn.edu.nju.candleflame.architecturedemo.mvc.model;

import android.graphics.Color;

import cn.edu.nju.candleflame.architecturedemo.mvc.bean.NumberBean;

public class NumberModel {

    NumberBean number;

    public NumberModel(){
        number = new NumberBean();
    }

    public int getNum(){
        return number.getNum();
    }
    public void setNum(int num){
        number.setNum(num);
    }

    public int getColor() {
        return number.getNum() % 2 == 0 ? Color.GRAY : Color.BLACK;
    }

    public void Plus(){
        number.setNum(number.getNum()+1);
    }
    public void Minus(){
        number.setNum(number.getNum()-1);
    }
}
