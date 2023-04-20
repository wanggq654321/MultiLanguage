package cn.multi.language;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.tencent.mmkv.MMKV;

import java.util.List;
import java.util.Locale;
import java.util.WeakHashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.view.LayoutInflaterCompat;
import cn.multi.language.bean.LanguageInfo;
import cn.multi.language.constant.Globel;
import cn.multi.language.utils.LanguageChangeUtils;
import cn.multi.language.utils.ReflectionUtils;
/**
 * {
 * "datas": [
 * {
 * "fileName": "i18n_android.json",
 * "fileUrl": "https://picobd.yunxuetang.cn/orgsv2/glbl/1676538098059/all/zh/custom/i18n_android.json",
 * "langTag": "zh",
 * "appType": "android",
 * "moduleTag": "all"
 * },
 * {
 * "fileName": "i18n_android.json",
 * "fileUrl": "https://picobd.yunxuetang.cn/orgsv2/glbl/1676538098059/all/vi/custom/i18n_android.json",
 * "langTag": "vi",
 * "appType": "android",
 * "moduleTag": "all"
 * },
 * {
 * "fileName": "i18n_android.json",
 * "fileUrl": "https://picobd.yunxuetang.cn/orgsv2/glbl/1676538098059/all/en/custom/i18n_android.json",
 * "langTag": "en",
 * "appType": "android",
 * "moduleTag": "all"
 * },
 * {
 * "fileName": "i18n_android.json",
 * "fileUrl": "https://picobd.yunxuetang.cn/orgsv2/glbl/1676538098059/all/ha/custom/i18n_android.json",
 * "langTag": "ha",
 * "appType": "android",
 * "moduleTag": "all"
 * }
 * ]
 * }
 */

/**
 * Context 对应一个页面的int-- @StringRes int 引用
 */
public class MultiResUtils {


    /**
     * 非必需初始化，初始化后可以更快的回收内存
     *
     * @param application
     */
    public static void init(Application application) {
        String rootDir = MMKV.initialize(application);
        System.out.println("mmkv root: " + rootDir);

        Globel.currentLug = LanguageChangeUtils.getCurrentLanguage();

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                LayoutInflater layoutInflater = LayoutInflater.from(activity);
                // Resources resources = new Resources(activity.getAssets(),activity.getDisplay(),activity.getCon);

//                ReflectionUtils.setValue("mResources",resources, resources);
//                        .with(activityContext).field("mResources").set(textRepairProxyResourcess);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                contextCacheKeys.remove(activity);
            }
        });

        //  Application启动的时候将Application的mResources对象Hook掉并设置TextResResources对象
        // ReflectionUtils.with(appContext).field("mResources").set(textRepairProxyResourcess);
        // Activity启动的时候将Activity的mResources对象Hook掉并设置TextResResources对象
        // Reflector.with(activityContext).field("mResources").set(textRepairProxyResourcess);

    }

    /**
     * 全局弱缓存 -- 字符key缓存池
     */
    public final static WeakHashMap<Integer, CharSequence> allCachekeys = new WeakHashMap<>();
    /**
     * 局部弱缓存 -- Context -- 弱引用页面的资源
     */
    public final static WeakHashMap<Context, SparseArray<CharSequence>> contextCacheKeys = new WeakHashMap<>();


    public static CharSequence getString(Context context, @StringRes int resid) {
        SparseArray<CharSequence> sparseArray = contextCacheKeys.get(context);
        if (sparseArray == null) {
            sparseArray = new SparseArray<>();
            contextCacheKeys.put(context, sparseArray);
        }
        CharSequence readCharSequence = sparseArray.get(resid);
        if (readCharSequence == null) {
            readCharSequence = allCachekeys.get(resid);
            // 读取 json 数据或者
            if (readCharSequence == null) {
                readCharSequence = readCharSequence(context, resid);
                sparseArray.put(resid, readCharSequence);
                // 临时缓存-全部keys缓存
                allCachekeys.put(resid, readCharSequence);
            }
        }
        return readCharSequence;
    }

    /**
     * 读取服务器动态下发的json文件
     * 直接序列到 k-v 本地文件中
     *
     * @return
     */
    private static CharSequence readCharSequence(Context context, @StringRes int resid) {
        CharSequence charSequence = context.getResources().getString(resid);
        String resKey = context.getResources().getResourceEntryName(resid);
        MMKV kv = MMKV.mmkvWithID(Globel.currentLug);
        String value = kv.getString(resKey, "");
        Log.d("MultiResUtils-缓存没有触发本地读取", "key: " + resKey + " value：" + value);
        return value;
    }


    public static void setCurrentLanguage(String local, String version) {
        MMKV kvGloble = MMKV.defaultMMKV();
        Globel.currentLug = local;
        SharedPreferences.Editor editorDafualt = kvGloble.edit();
        editorDafualt.putString(Globel.CURRENT_LANGUAGE, local);
        editorDafualt.putString(Globel.CURRENT_LANGUAGE_VERSION, version);
        editorDafualt.commit();

        allCachekeys.clear();
        contextCacheKeys.clear();
    }


}
