package com.example.multilanguage;

import androidx.appcompat.app.AppCompatActivity;
import cn.multi.language.MultiResUtils;
import cn.multi.language.bean.LanguageInfo;
import cn.multi.language.utils.AssetsUtils;
import cn.multi.language.utils.LanguageChangeUtil;
import cn.multi.language.utils.LanguageChangeUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiResUtils.init(getApplication());


        String jsonStr = AssetsUtils.getFileFromAssets(this, "lang-zh.json");
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            LanguageInfo languageInfo = new LanguageInfo();
            languageInfo.setLocal("zh");
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = jsonObject.getString(key);
                languageInfo.getList().add(new LanguageInfo.StringPair(key, value));
            }
            LanguageChangeUtils.write(languageInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonStr2 = AssetsUtils.getFileFromAssets(this, "lang-en.json");
        try {
            JSONObject jsonObject = new JSONObject(jsonStr2);
            LanguageInfo languageInfo2 = new LanguageInfo();
            languageInfo2.setLocal("en");
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = jsonObject.getString(key);
                languageInfo2.getList().add(new LanguageInfo.StringPair(key, value));
            }
            LanguageChangeUtils.write(languageInfo2);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

        TextView tv_test = findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MultiResUtils: ", "allkeys: " + MultiResUtils.allCachekeys.size() + "  contextKeys:" + MultiResUtils.contextCacheKeys.size());
                tv_test.setText(MultiResUtils.getString(MainActivity.this, R.string.app_comp_yxt_photo_msg_photodisabledsave));
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