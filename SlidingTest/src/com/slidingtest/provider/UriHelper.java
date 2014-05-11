package com.slidingtest.provider;

import android.net.Uri;

public class UriHelper {


    public static Uri getUri(String path) {
        Uri uri = prepare(path).build();
        return uri;
    }

    private static Uri.Builder prepare(String path) {
        return new Uri.Builder().scheme("content").authority(DbProvider.AUTHORITY).path(path).query("").fragment("");
    }

    static Uri removeQuery(Uri uri) {
        Uri newUri = uri.buildUpon().query("").build();
        return newUri;
    }

}
