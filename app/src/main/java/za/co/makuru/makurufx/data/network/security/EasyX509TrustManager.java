package za.co.makuru.makurufx.data.network.security;

import android.annotation.SuppressLint;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class EasyX509TrustManager implements X509TrustManager {

    /**
     * Constructor for EasyX509TrustManager.
     */
    public EasyX509TrustManager()
            throws NoSuchAlgorithmException, KeyStoreException {
        super();
        TrustManagerFactory factory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init((KeyStore) null);
        TrustManager[] trustmanagers = factory.getTrustManagers();
        if (trustmanagers.length == 0) {
            throw new NoSuchAlgorithmException("no trust manager found");
        }
    }

    /**
     * @see javax.net.ssl.X509TrustManager#checkClientTrusted(X509Certificate[],
     * String authType)
     */
    @SuppressLint("TrustAllX509TrustManager")
    public void checkClientTrusted(X509Certificate[] certificates,
                                   String authType) throws CertificateException {
    }

    /**
     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(X509Certificate[],
     * String authType)
     */
    @SuppressLint("TrustAllX509TrustManager")
    public void checkServerTrusted(X509Certificate[] certificates,
                                   String authType) throws CertificateException {
    }

    /**
     * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
     */
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

}

