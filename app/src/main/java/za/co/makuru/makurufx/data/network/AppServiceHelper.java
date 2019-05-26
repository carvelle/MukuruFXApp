package za.co.makuru.makurufx.data.network;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Single;
import za.co.makuru.makurufx.data.network.httpclient.HttpClient;
import za.co.makuru.makurufx.data.network.httpclient.OkHttpClientFactory;
import za.co.makuru.makurufx.data.network.model.request.CurrencyListRequest;
import za.co.makuru.makurufx.data.network.model.request.LatestRequest;
import za.co.makuru.makurufx.data.network.model.response.CurrencyListResponse;
import za.co.makuru.makurufx.data.network.model.response.LatestResponse;

public class AppServiceHelper implements ServiceHelper {

    private OkHttpClientFactory mOkHttpClientBuilderFactory;
    @Inject
    public AppServiceHelper(OkHttpClientFactory okHttpClientBuilderFactory) {

        this.mOkHttpClientBuilderFactory = okHttpClientBuilderFactory;
    }

    @Override
    public Single<CurrencyListResponse> getAllCurrencies(CurrencyListRequest request, String path) {

        return HttpClient.get(ServiceEndPoint.BaseUrl)
                .addQueryParameter(new HashMap<>())
                .addPathParameter("1",path)
                .setOkHttpClient(mOkHttpClientBuilderFactory.SecureClient().build())
                .build()
                .getObjectSingle(CurrencyListResponse.class);
    }

    @Override
    public Single<LatestResponse> getLatest(LatestRequest request, String path) {
        return HttpClient.get(ServiceEndPoint.BaseUrl)
                .addQueryParameter(request)
                .addPathParameter("1",path)
                .setOkHttpClient(mOkHttpClientBuilderFactory.SecureClient().build())
                .build()
                .getObjectSingle(LatestResponse.class);
    }
}
