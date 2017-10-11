package com.andy.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;

import static android.R.attr.targetClass;

/**
 * Created by Andy on 2017/10/10.
 */

public class Router {
    private static Router instance = null;
    private Map<String, Object> nodeRouteMap;
    private IntentBuilder intentBuilder;

    public synchronized static Router getInstance() {
        if (instance == null) {
            synchronized (Router.class) {
                instance = new Router();
            }
        }
        return instance;
    }

    private Router() {}

    public void init(Map<String, Object> map) {
        nodeRouteMap = map;
    }

    private Object getClazz(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return nodeRouteMap.get(path);
    }

    public void navigateTo(Context context, String path) {
        Class<?> targetClass = (Class<?>) getClazz(path);
        if (targetClass!=null) {
            context.startActivity(new Intent(context, targetClass));
        }
    }

    /**
     * navigate with intent builder
     */
    public void navigate() {
        if (intentBuilder !=null) {
            if (intentBuilder.forResult) {
                if (intentBuilder.context instanceof Activity) {
                    ActivityCompat.startActivityForResult((Activity)intentBuilder.context,
                            intentBuilder.intent,
                            intentBuilder.requestCode, null);
                } else {
                    Log.e("Router", "class, " + intentBuilder.context.getClass().getName()
                            +" is not Activity cannot startActivityForResult." );
                    intentBuilder.context.startActivity(intentBuilder.intent);
                }
            } else {
                intentBuilder.context.startActivity(intentBuilder.intent);
            }
        }
    }

    public static class IntentBuilder {
        Intent intent;
        private Bundle bundle = new Bundle();
        Context context;
        private boolean forResult;
        private int requestCode;
        Router router;

        public IntentBuilder(Context context) {
            this.context = context;
            router = Router.getInstance();
        }

        public IntentBuilder setPath(String path) {
            Class<? extends Activity> clazz = (Class<? extends Activity>) router.getClazz(path);

            if (clazz !=null) {
                intent = new Intent(context, clazz);
                intent.putExtra("data", bundle);
            } else {
                throw new NullPointerException("class of activity is null");
            }
            return this;
        }

        public IntentBuilder withString(String key, String value) {
            if (TextUtils.isEmpty(key)) {
                throw new NullPointerException("key cannot be empty");
            }
            if (TextUtils.isEmpty(value)) {
                throw new NullPointerException("value cannot be empty");
            }
            bundle.putString(key, value);
            return this;
        }

        public IntentBuilder withInt(String key, Integer value) {
            if (TextUtils.isEmpty(key)) {
                throw new NullPointerException("key cannot be empty");
            }
            if (null == value) {
                throw new NullPointerException("value cannot be null");
            }
            bundle.putInt(key, value);
            return this;
        }

        public IntentBuilder withBoolean(String key, Boolean value) {
            if (TextUtils.isEmpty(key)) {
                throw new NullPointerException("key cannot be empty");
            }
            if (null == value) {
                throw new NullPointerException("value cannot be null");
            }
            bundle.putBoolean(key, value);
            return this;
        }

        public IntentBuilder withByte(String key, Byte value) {
            if (TextUtils.isEmpty(key)) {
                throw new NullPointerException("key cannot be empty");
            }
            if (null == value) {
                throw new NullPointerException("value cannot be null");
            }
            bundle.putByte(key, value);
            return this;
        }

        public IntentBuilder withDouble(String key, Double value) {
            if (TextUtils.isEmpty(key)) {
                throw new NullPointerException("key cannot be empty");
            }
            if (null == value) {
                throw new NullPointerException("value cannot be null");
            }
            bundle.putDouble(key, value);
            return this;
        }

        public IntentBuilder withFloat(String key, Float value) {
            if (TextUtils.isEmpty(key)) {
                throw new NullPointerException("key cannot be empty");
            }
            if (null == value) {
                throw new NullPointerException("value cannot be null");
            }
            bundle.putFloat(key, value);
            return this;
        }

        public IntentBuilder setRequstCode(int requestCode) {
            this.requestCode = requestCode;
            this.forResult = true;
            return this;
        }

        public Router buildRouter() {
            router.intentBuilder = this;
            return router;
        }
    }
}
