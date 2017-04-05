package com.jameschen.comm.utils;

import android.content.Context;

import com.squareup.otto.Bus;


public class OttoBusHelper {

    private static Bus bus;

    private OttoBusHelper() {
    }

    public static Bus getBus(Context mContext) {

        if (null == bus) {

            bus = new Bus();
        }
        return bus;
    }

}
