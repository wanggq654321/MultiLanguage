package cn.multi.language.utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.tencent.mmkv.MMKV;

import cn.multi.language.MultiResUtils;
import cn.multi.language.bean.LanguageInfo;
import cn.multi.language.constant.Globel;

public class LanguageChangeUtils {


    /**
     * 写入本地，便于 k-v 读取
     */
    public static void write(LanguageInfo languageInfo) {
        MMKV kv = MMKV.mmkvWithID(languageInfo.getLocal());
        long last = System.currentTimeMillis();
        SharedPreferences.Editor editor = kv.edit();
        for (LanguageInfo.StringPair stringPair : languageInfo.getList()) {
            editor.putString(stringPair.getKey(), stringPair.getValue());
        }
        editor.commit();

        Log.e("MultiResUtils: ", " 数据长度size: " + languageInfo.getList().size() + " write写入耗时: " + (System.currentTimeMillis() - last) + " 毫秒");


        long lastRead = System.currentTimeMillis();
        for (LanguageInfo.StringPair stringPair : languageInfo.getList()) {
            String value = kv.getString(stringPair.getKey(), "");
            // Log.e("MultiResUtils: ", " key: " + stringPair.getKey() + "    value: " + value);
        }
        Log.e("MultiResUtils: ", " 数据长度size: " + languageInfo.getList().size() + " read读取耗时: " + (System.currentTimeMillis() - lastRead) + " 毫秒");
    }


    public static String getCurrentLanguage() {
        MMKV kvGloble = MMKV.defaultMMKV();
        return kvGloble.getString(Globel.CURRENT_LANGUAGE, "zh");
    }




}
