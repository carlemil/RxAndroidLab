package com.jayway.rxandroidlab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.content_main)
    LinearLayout contentMain;

    String weather = "[{\"temp\":21,\"wind\":\"4\"},\n" +
            "{\"temp\":20,\"wind\":\"1\"},\n" +
            "{\"temp\":18,\"wind\":\"2\"},\n" +
            "{\"temp\":17,\"wind\":\"1\"},\n" +
            "{\"temp\":22,\"wind\":\"3\"},\n" +
            "{\"temp\":25,\"wind\":\"7\"},\n" +
            "{\"temp\":17,\"wind\":\"15\"},\n" +
            "{\"temp\":21,\"wind\":\"14\"},\n" +
            "{\"temp\":20,\"wind\":\"12\"},\n" +
            "{\"temp\":18,\"wind\":\"9\"},\n" +
            "{\"temp\":24,\"wind\":\"10\"},\n" +
            "{\"temp\":17,\"wind\":\"6\"}]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Day[] days = new Gson().fromJson(weather, Day[].class);

        for (Day day : days) {
            showDayInList(day);
        }
    }

    private void showDayInList(Day day) {
        TextView textView = new TextView(this);
        textView.setText("Temperature: " + day.temp + ", Wind: " + day.wind);
        contentMain.addView(textView);
    }

}
