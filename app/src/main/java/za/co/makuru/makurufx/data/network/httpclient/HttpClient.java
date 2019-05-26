package za.co.makuru.makurufx.data.network.httpclient;

public class HttpClient {

    public static HttpRequest.PostRequestBuilder post(String url) {
        return new HttpRequest.PostRequestBuilder(url);
    }

    public static HttpRequest.GetRequestBuilder get(String url) {
        return new HttpRequest.GetRequestBuilder(url);
    }
}
