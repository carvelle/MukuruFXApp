package za.co.makuru.makurufx.data.network;

import io.reactivex.Single;
import za.co.makuru.makurufx.data.network.model.request.CurrencyListRequest;
import za.co.makuru.makurufx.data.network.model.request.LatestRequest;
import za.co.makuru.makurufx.data.network.model.response.CurrencyListResponse;
import za.co.makuru.makurufx.data.network.model.response.LatestResponse;

public interface ServiceHelper {
    Single<CurrencyListResponse> getAllCurrencies(CurrencyListRequest request, String path);

    Single<LatestResponse> getLatest(LatestRequest request, String path);

}
