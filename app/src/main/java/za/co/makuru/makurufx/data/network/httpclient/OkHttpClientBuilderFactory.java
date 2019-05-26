package za.co.makuru.makurufx.data.network.httpclient;

import android.content.Context;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.internal.JavaNetCookieJar;
import za.co.makuru.makurufx.data.network.security.CustomSSLSocketFactory;
import za.co.makuru.makurufx.data.network.security.EasyX509TrustManager;
import za.co.makuru.makurufx.data.network.session.PersistentCookieStore;
import za.co.makuru.makurufx.di.ApplicationContext;

public class OkHttpClientBuilderFactory implements OkHttpClientFactory {

    Context mContext;

    @Inject
    public OkHttpClientBuilderFactory(@ApplicationContext Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public  OkHttpClient.Builder SecureClient(){
        SSLSocketFactory sslSocketFactory = new CustomSSLSocketFactory();
        try {
            CookieHandler cookieHandler = new CookieManager(
                    new PersistentCookieStore(mContext), CookiePolicy.ACCEPT_ALL);
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_1,TlsVersion.TLS_1_2)
                    .cipherSuites(CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256)
                    .build();
            EasyX509TrustManager easyX509TrustManager = new EasyX509TrustManager();
            OkHttpClient.Builder client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, easyX509TrustManager)
                    .cookieJar(new JavaNetCookieJar(cookieHandler))
                    .connectionSpecs(Collections.singletonList(spec))
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .callTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS);

            return client;

        }catch (Exception nsa){
            return new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS);

        }
    }
}