package cn.multi.language.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

@Deprecated
public class LanguageChangeUtil {


    public static Context changeAppLanguage(String language, Context context) {
        Resources resources = context.getApplicationContext().getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Locale locale = null;
        byte var8 = -1;
        switch (language.hashCode()) {
            case 3321:
                if (language.equals("ha")) {
                    var8 = 1;
                }
                break;
            case 3763:
                if (language.equals("vi")) {
                    var8 = 2;
                }
                break;
            case 3886:
                if (language.equals("zh")) {
                    var8 = 0;
                }
        }
        switch (var8) {
            case 0:
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case 1:
                locale = Locale.TRADITIONAL_CHINESE;
                break;
            case 2:
                locale = new Locale("vi", "VN");
                break;
            default:
                locale = Locale.ENGLISH;
        }
        mCurrentSystemLocal = locale;
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, dm);
        return context.createConfigurationContext(configuration);
    }

    public static Locale mCurrentSystemLocal = Locale.SIMPLIFIED_CHINESE;

    /**
     * 设置语言
     */
    public static void setConfiguration(Context context) {
        if (context == null) {
            return;
        }
        Context appContext = context.getApplicationContext();
        Locale targetLocale = mCurrentSystemLocal;
        Locale.setDefault(targetLocale);
        Configuration configuration = appContext.getResources().getConfiguration();
        configuration.setLocale(targetLocale);
        context.createConfigurationContext(configuration);
        Resources resources = appContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);//语言更换生效的代码!
    }


    public static Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context);
        } else {
            setConfiguration(context);
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = mCurrentSystemLocal;
        Log.d("TAG", "getLanguage ${getLanguage(locale)}");
        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }


}
