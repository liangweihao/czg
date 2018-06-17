package com.gcyh.jiedian.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * SharedPreferences工具
 * Created by caizhiguang on 18/4/11.
 */
public class SPUtil {
    private static SharedPreferences sp;
    private static String XML_NAME = "daikuanguanjia";

    public static void setBoolean(Context ctx, String key, boolean value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    public static void setString(Context ctx, String key, String value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public static void setInt(Context ctx, String key, int value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }


    public static int getInt(Context ctx, String key, int defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    public static void setFloat(Context ctx, String key, float value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putFloat(key, value).commit();
    }

    public static Float getFloat(Context ctx, String key, float defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        return sp.getFloat(key, defValue);
    }

    public static void setLong(Context ctx, String key, long value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).commit();
    }

    public static Long getLong(Context ctx, String key, long defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        return sp.getLong(key, defValue);
    }

    public static void setList(Context ctx, String key, Set<String> value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putStringSet(key, value).commit();
    }

    public static Set<String> getList(Context ctx, String key, Set<String> defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        return sp.getStringSet(key, defValue);
    }

    public static void remove(Context ctx, String key) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }

    public static void saveInfo(Context context, String key, List<Map<String, String>> data) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> itemMap = data.get(i);
            Iterator<Map.Entry<String, String>> iterator = itemMap.entrySet().iterator();

            JSONObject object = new JSONObject();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    object.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mJsonArray.put(object);
        }

        SharedPreferences sp = context.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, mJsonArray.toString());
        editor.apply();
    }

    public static List<Map<String, String>> getInfo(Context context, String key) {
        List<Map<String, String>> data = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
        String result = sp.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Map<String, String> itemMap = new HashMap<>();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
                data.add(itemMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}

