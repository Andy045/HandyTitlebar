package com.handy.widget.titlebar.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.handy.widget.titlebar.HandyTitleBar;
import com.handy.widget.titlebar.entity.Action;

public class TestActivity extends AppCompatActivity {

    private HandyTitleBar handyTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        handyTitleBar = findViewById(R.id.titlebarNew);
        handyTitleBar.showCustomStatusBar(TestActivity.this);
        handyTitleBar.setTitleBarContent("主标题\n副标题");
        handyTitleBar.addLeftAction(new Action() {
            {
                setText("返回");
                setTextMarginLeft(0);
                setImageSrc(R.drawable.hdb_back_n);
                syncTextImage(R.color.myTitleBar_TestColor_BottomLine, R.color.myTitleBar_TestColor_ActionText);
            }

            @Override
            public void onClick() {
                Toast.makeText(TestActivity.this, "!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
