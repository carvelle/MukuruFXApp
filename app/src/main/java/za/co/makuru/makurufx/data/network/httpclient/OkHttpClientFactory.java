package za.co.makuru.makurufx.data.network.httpclient;

import okhttp3.OkHttpClient;

public interface OkHttpClientFactory {

    OkHttpClient.Builder SecureClient();
}
