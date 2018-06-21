package cn.edu.nju.candleflame.architecturedemo.mvc.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.nju.candleflame.architecturedemo.R;
import cn.edu.nju.candleflame.architecturedemo.mvc.controller.NumberController;
import cn.edu.nju.candleflame.architecturedemo.mvc.model.NumberModel;

public class MVCFragment extends Fragment implements View.OnClickListener {

    private NumberController numberController;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mvcdemo, container, false);
        this.rootView = rootView;

        this.numberController = new NumberController();
        refresh(numberController.getNumberModel());

        Button plusButton = rootView.findViewById(R.id.mvc_add);
        plusButton.setOnClickListener(this);
        Button minusButton = rootView.findViewById(R.id.mvc_sub);
        minusButton.setOnClickListener(this);

        return rootView;
    }

    private void refresh(NumberModel model) {
        TextView textView = rootView.findViewById(R.id.mvc_number);
        textView.setText(String.valueOf(model.getNum()));
        textView.setTextColor(model.getColor());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mvc_add:
                numberController.plusOne(this::refresh);
                break;
            case R.id.mvc_sub:
                numberController.minusOne(this::refresh);
                break;
        }
    }
}
