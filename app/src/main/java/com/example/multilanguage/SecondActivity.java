package com.example.multilanguage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import cn.multi.language.MultiResUtils;
import cn.multi.language.utils.LanguageChangeUtil;
import cn.multi.language.utils.LanguageChangeUtils;

public class SecondActivity extends AppCompatActivity {

    static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView tv_test = findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MultiResUtils: ", "allkeys: " + MultiResUtils.allCachekeys.size() + "  contextKeys:" + MultiResUtils.contextCacheKeys.size());

                 tv_test.setText(MultiResUtils.getString(SecondActivity.this, R.string.common_i18n_country_areacode_30_68));
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i % 2 == 0) {
                    MultiResUtils.setCurrentLanguage("zh","1");
                    // LanguageChangeUtil.changeAppLanguage("zh", SecondActivity.this);
                } else {
                   // LanguageChangeUtil.changeAppLanguage("en", SecondActivity.this);
                    MultiResUtils.setCurrentLanguage("en","1");
                }

            }
        });


    }


    @Override
    protected void attachBaseContext(Context newBase) {
        Log.e("TAG", "attachBaseContext");
        super.attachBaseContext(LanguageChangeUtil.attachBaseContext(newBase));
        //app杀进程启动后会调用Activity attachBaseContext
        LanguageChangeUtil.setConfiguration(newBase);
    }

}