package cn.multi.language.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetsUtils {

    public static String getFileFromAssets(Context mContext, String fileName) {
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(am.open(fileName)));

            String next;
            while(null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException var6) {
            sb.delete(0, sb.length());
        }

        return sb.toString().trim();
    }

}
