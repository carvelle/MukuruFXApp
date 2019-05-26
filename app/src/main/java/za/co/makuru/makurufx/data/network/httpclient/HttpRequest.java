package za.co.makuru.makurufx.data.network.httpclient;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Okio;
import za.co.makuru.makurufx.data.network.Enums.RequestType;
import za.co.makuru.makurufx.data.network.Enums.ResponseType;
import za.co.makuru.makurufx.data.network.error.CustomError;
import za.co.makuru.makurufx.data.network.model.BaseResponse;
import za.co.makuru.makurufx.utils.NetworkUtils;
import za.co.makuru.makurufx.utils.ParseUtil;

import static za.co.makuru.makurufx.data.network.Method.GET;
import static za.co.makuru.makurufx.data.network.Method.POST;
import static za.co.makuru.makurufx.data.network.NetworkInternals.generateSimpleObservable;

public class HttpRequest<T> {

    private String mUrl;
    private Type mType = null;
    private OkHttpClient mOkHttpClient;
    ResponseType mResponseType;
    private int mRequestType;
    private int mMethod;
    private HashMap<String, String> mBodyParameterMap = new HashMap<>();
    private HashMap<String, String> mUrlEncodedFormBodyParameterMap = new HashMap<>();
    private HashMap<String, List<String>> mQueryParameterMap = new HashMap<>();
    private HashMap<String, String> mPathParameterMap = new HashMap<>();

    private Call call;

    public HttpRequest(PostRequestBuilder builder){

        this.mRequestType = RequestType.SIMPLE;
        this.mMethod = builder.mMethod;
        this.mUrl = builder.mUrl;
        this.mBodyParameterMap = builder.mBodyParameterMap;
        this.mUrlEncodedFormBodyParameterMap = builder.mUrlEncodedFormBodyParameterMap;
        this.mQueryParameterMap = builder.mQueryParameterMap;
        this.mPathParameterMap = builder.mPathParameterMap;
        this.mOkHttpClient = builder.mOkHttpClient;

    }

    public HttpRequest(GetRequestBuilder builder){
        this.mRequestType = RequestType.SIMPLE;
        this.mMethod = builder.mMethod;
        this.mUrl = builder.mUrl;
        this.mQueryParameterMap = builder.mQueryParameterMap;
        this.mPathParameterMap = builder.mPathParameterMap;

        this.mOkHttpClient = builder.mOkHttpClient;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public int getRequestType() {
        return mRequestType;
    }

    public int getMethod() {
        return mMethod;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void setOkHttpClient(OkHttpClient mOkHttpClient) {
        this.mOkHttpClient = mOkHttpClient;
    }

    public void setResponseAs(ResponseType responseType) {
        this.mResponseType = responseType;
    }

    public String getUrl() {
        String tempUrl = mUrl;
        for (HashMap.Entry<String, String> entry : mPathParameterMap.entrySet()) {
            tempUrl = tempUrl.replace("{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
        }
        HttpUrl.Builder urlBuilder = HttpUrl.parse(tempUrl).newBuilder();
        if (mQueryParameterMap != null) {
            Set<Map.Entry<String, List<String>>> entries = mQueryParameterMap.entrySet();
            for (Map.Entry<String, List<String>> entry : entries) {
                String name = entry.getKey();
                List<String> list = entry.getValue();
                if (list != null) {
                    for (String value : list) {
                        urlBuilder.addQueryParameter(name, value);
                    }
                }
            }
        }
        return urlBuilder.build().toString();
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public <T> Observable<T> getObjectObservable(Class<T> objectClass) {
        this.setType(objectClass);
        this.setResponseAs(ResponseType.PARSED);

        return generateSimpleObservable(this);

    }

    public <T> Single<T> getObjectSingle(Class<T> objectClass) {
        return getObjectObservable(objectClass).singleOrError();
    }

    public static class PostRequestBuilder<T extends PostRequestBuilder> implements RequestBuilder {

        private int mMethod = POST;
        private String mUrl;
        private HashMap<String, String> mBodyParameterMap = new HashMap<>();
        private HashMap<String, List<String>> mQueryParameterMap = new HashMap<>();
        private OkHttpClient mOkHttpClient;
        private HashMap<String, String> mUrlEncodedFormBodyParameterMap = new HashMap<>();
        private HashMap<String, String> mPathParameterMap = new HashMap<>();


        public PostRequestBuilder(String url) {
            this.mUrl = url;
            this.mMethod = POST;
        }

        public PostRequestBuilder(String url, int method) {
            this.mUrl = url;
            this.mMethod = method;
        }

        @Override
        public T addQueryParameter(Map<String, String> queryParameterMap) {
            if (queryParameterMap != null) {
                for (HashMap.Entry<String, String> entry : queryParameterMap.entrySet()) {
                    addQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            return (T) this;
        }

        @Override
        public T addQueryParameter(String key, String value) {
            List<String> list = mQueryParameterMap.get(key);
            if (list == null) {
                list = new ArrayList<>();
                mQueryParameterMap.put(key, list);
            }
            if (!list.contains(value)) {
                list.add(value);
            }
            return (T) this;
        }

        @Override
        public T addPathParameter(String key, String value) {
            mPathParameterMap.put(key, value);
            return (T) this;
        }

        @Override
        public RequestBuilder addQueryParameter(Object object) {
            return null;
        }


        @Override
        public T addHeaders(String key, String value) {

            return (T) this;
        }

        @Override
        public T setOkHttpClient(OkHttpClient okHttpClient) {
            mOkHttpClient = okHttpClient;
            return (T) this;
        }

        public T addBodyParameter(Object object) {
            if (object != null) {
                mBodyParameterMap.putAll(ParseUtil
                        .getParserFactory()
                        .getStringMap(object));
            }
            return (T) this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }

    public static class GetRequestBuilder<T extends GetRequestBuilder> implements RequestBuilder {
        private int mMethod = GET;
        private String mUrl;
        private HashMap<String, List<String>> mQueryParameterMap = new HashMap<>();
        private HashMap<String, String> mPathParameterMap = new HashMap<>();

        private OkHttpClient mOkHttpClient;

        public GetRequestBuilder(String url) {
            this.mUrl = url;
            this.mMethod = GET;
        }

        public GetRequestBuilder(String url, int method) {
            this.mUrl = url;
            this.mMethod = method;
        }


        @Override
        public T addQueryParameter(String key, String value) {
            List<String> list = mQueryParameterMap.get(key);
            if (list == null) {
                list = new ArrayList<>();
                mQueryParameterMap.put(key, list);
            }
            if (!list.contains(value)) {
                list.add(value);
            }
            return (T) this;
        }

        @Override
        public T addQueryParameter(Map<String, String> queryParameterMap) {
            if (queryParameterMap != null) {
                for (HashMap.Entry<String, String> entry : queryParameterMap.entrySet()) {
                    addQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            return (T) this;
        }

        @Override
        public T addQueryParameter(Object object) {
            if (object != null) {
                return addQueryParameter(ParseUtil
                        .getParserFactory()
                        .getStringMap(object));
            }
            return (T) this;
        }

        @Override
        public T addHeaders(String key, String value) {

            return (T) this;
        }


        @Override
        public T addPathParameter(String key, String value) {
            mPathParameterMap.put(key, value);
            return (T) this;
        }


        @Override
        public T setOkHttpClient(OkHttpClient okHttpClient) {
            mOkHttpClient = okHttpClient;
            return (T) this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }

    public RequestBody getRequestBody() {

        FormBody.Builder builder = new FormBody.Builder();
        try {
            for (HashMap.Entry<String, String> entry : mBodyParameterMap.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            for (HashMap.Entry<String, String> entry : mUrlEncodedFormBodyParameterMap.entrySet()) {
                builder.addEncoded(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();

    }

    public BaseResponse parseResponse(Response response) {
        switch (mResponseType) {

            case JSON_OBJECT:
                try {
                    JSONObject json = new JSONObject(Okio.buffer(response.body()
                            .source()).readUtf8());
                    return BaseResponse.success(json);
                } catch (Exception e) {
                    return BaseResponse.failed(NetworkUtils.getErrorForParse(new CustomError(e)));
                }
            case STRING:
                try {
                    return BaseResponse.success(Okio.buffer(response
                            .body().source()).readUtf8());
                } catch (Exception e) {
                    return BaseResponse.failed(NetworkUtils.getErrorForParse(new CustomError(e)));
                }
            case PARSED:

                try {
                    return BaseResponse.success(ParseUtil.getParserFactory()
                            .responseBodyParser(mType).convert(response.body()));
                } catch (Exception e) {
                    return BaseResponse.failed(NetworkUtils.getErrorForParse(new CustomError(e)));
                }

        }
        return null;
    }

    public CustomError parseNetworkError(CustomError customError) {
        try {
            if (customError.getResponse() != null && customError.getResponse().body() != null
                    && customError.getResponse().body().source() != null) {
                customError.setErrorBody(Okio.buffer(customError
                        .getResponse().body().source()).readUtf8());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customError;
    }

    public ResponseType getResponseAs() {
        return mResponseType;
    }
}

