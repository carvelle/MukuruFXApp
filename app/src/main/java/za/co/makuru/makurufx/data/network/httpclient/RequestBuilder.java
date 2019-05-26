package za.co.makuru.makurufx.data.network.httpclient;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public interface RequestBuilder {

    RequestBuilder addHeaders(String key ,String value);

    RequestBuilder addQueryParameter(String key, String value);

    RequestBuilder addQueryParameter(Map<String, String> queryParameterMap);

    RequestBuilder setOkHttpClient(OkHttpClient okHttpClient);

    RequestBuilder addPathParameter(String key, String value);

    RequestBuilder addQueryParameter(Object object);

}