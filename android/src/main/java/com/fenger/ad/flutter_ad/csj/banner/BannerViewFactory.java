package com.fenger.ad.flutter_ad.csj.banner;

import android.app.Activity;
import android.content.Context;


import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class BannerViewFactory extends PlatformViewFactory {

    private final BinaryMessenger messenger;
    private final Activity activity;
    public BannerViewFactory(Activity activity, BinaryMessenger messenger) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;
        this.activity = activity;
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new BannerView(activity, messenger, viewId, params);
    }
}
