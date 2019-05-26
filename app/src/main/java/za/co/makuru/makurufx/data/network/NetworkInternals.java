package za.co.makuru.makurufx.data.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import za.co.makuru.makurufx.data.network.error.CustomError;
import za.co.makuru.makurufx.data.network.httpclient.HttpRequest;
import za.co.makuru.makurufx.data.network.model.BaseResponse;
import za.co.makuru.makurufx.utils.NetworkUtils;

import static za.co.makuru.makurufx.data.network.Method.GET;
import static za.co.makuru.makurufx.data.network.Method.POST;

public class NetworkInternals {

    public static OkHttpClient sHttpClient = getClient();

    public static <T> Observable<T> generateSimpleObservable(HttpRequest request) {
        Request okHttpRequest;
        Request.Builder builder = new Request.Builder().url(request.getUrl());
        RequestBody requestBody;
        switch (request.getMethod()) {
            case GET: {
                builder = builder.get();
                break;
            }
            case POST: {
                requestBody = request.getRequestBody();
                builder = builder.post(requestBody);
                break;
            }

        }

        okHttpRequest = builder.build();
        if (request.getOkHttpClient() != null) {
            request.setCall(request
                    .getOkHttpClient()
                    .newBuilder()
                    .build()
                    .newCall(okHttpRequest));
        } else {
            request.setCall(sHttpClient.newCall(okHttpRequest));
        }
        return new SimpleObservable<>(request);
    }

    static final class SimpleObservable<T>  extends Observable<T> {

        private HttpRequest request;
        private final Call originalCall;

        SimpleObservable(HttpRequest request) {
            this.request = request;
            this.originalCall = request.getCall();
        }

        @Override
        protected void subscribeActual(Observer<? super T> observer) {
            Call call = originalCall.clone();
            observer.onSubscribe(new CustomDisposable(call));
            boolean doNotSwallowError = false;
            Response okHttpResponse = null;
            try {
                okHttpResponse = call.execute();

                if (okHttpResponse.code() >= 400) {
                    if (!call.isCanceled()) {
                        observer.onError(NetworkUtils.getErrorForServerResponse(new CustomError(okHttpResponse),
                                request, okHttpResponse.code()));
                    }
                } else {
                    BaseResponse<T> response = request.parseResponse(okHttpResponse);
                    if (!response.isSuccess()) {
                        if (!call.isCanceled()) {
                            observer.onError(response.getError());
                        }
                    } else {
                        if (!call.isCanceled()) {
                            observer.onNext(response.getResult());
                        }
                        if (!call.isCanceled()) {
                            doNotSwallowError = true;
                            observer.onComplete();
                        }
                    }
                }
            } catch (IOException ioe) {
                if (!call.isCanceled()) {
                    observer.onError(NetworkUtils.getErrorForConnection(new CustomError(ioe)));
                }
            } catch (Exception e) {
                Exceptions.throwIfFatal(e);
                if (doNotSwallowError) {
                    RxJavaPlugins.onError(e);
                } else if (!call.isCanceled()) {
                    try {
                        observer.onError(NetworkUtils.getErrorForConnection(new CustomError(e)));
                    } catch (Exception e1) {
                        Exceptions.throwIfFatal(e1);
                        RxJavaPlugins.onError(new CompositeException(e, e1));
                    }
                }
            } finally {
                NetworkUtils.CloseSource(okHttpResponse, request);
            }
        }
    }

    public static OkHttpClient getClient() {
        if (sHttpClient == null) {
            return getDefaultClient();
        }
        return sHttpClient;
    }


    public static OkHttpClient getDefaultClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    private static final class CustomDisposable implements Disposable {

        private final Call call;

        private CustomDisposable(Call call) {
            this.call = call;
        }

        @Override
        public void dispose() {
            this.call.cancel();
        }

        @Override
        public boolean isDisposed() {
            return this.call.isCanceled();
        }
    }


    public static void setClient(OkHttpClient okHttpClient) {
        sHttpClient = okHttpClient;
    }
}
