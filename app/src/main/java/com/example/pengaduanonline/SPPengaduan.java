package com.example.pengaduanonline;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SPPengaduan {
    public static final String SP_PENGADUAN = "spPengaduan";
    public static final String SP_USERNAME = "spUsername";
    public static final Boolean SP_INFO = false;

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    private static SPPengaduan instance = null;

    public SPPengaduan(Context context) {
        sp = context.getSharedPreferences(SP_PENGADUAN, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String k, String v) {
        spEditor.putString(k, v);
        spEditor.commit();
    }

    public void saveSPBoolean(String k, Boolean b) {
        spEditor.putBoolean(k, b);
        spEditor.commit();
    }

    public void clearSP(@NonNull Context context) {
        SharedPreferences spref = context.getSharedPreferences(SP_PENGADUAN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public boolean getSPLogin() {
        return sp.getBoolean(String.valueOf(SP_INFO), false);
    }

    public String getSpUsername (){
        return sp.getString(SP_USERNAME, "");
    }

}
