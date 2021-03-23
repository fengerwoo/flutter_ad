package com.fenger.ad.flutter_ad;

import java.util.Map;

public abstract class CallBack {
    Object ad = null;
    public abstract void call(Map<String, Object> map);

    public Object getAd(){
        return ad;
    }

    public void setAd(Object ad){
        this.ad = ad;
    }
}
