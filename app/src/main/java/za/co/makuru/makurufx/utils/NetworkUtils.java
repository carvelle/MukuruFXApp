package za.co.makuru.makurufx.utils;

import okhttp3.Response;
import za.co.makuru.makurufx.constants.Constant;
import za.co.makuru.makurufx.data.network.Enums.ResponseType;
import za.co.makuru.makurufx.data.network.error.CustomError;
import za.co.makuru.makurufx.data.network.httpclient.HttpRequest;

public class NetworkUtils {

    public static CustomError getErrorForConnection(CustomError error) {
        error.setErrorDetail(Constant.CONNECTION_ERROR);
        error.setErrorCode(0);
        return error;
    }


    public static CustomError getErrorForServerResponse(CustomError error, HttpRequest request, int code) {
        error = request.parseNetworkError(error);
        error.setErrorCode(code);
        error.setErrorDetail(Constant.RESPONSE_FROM_SERVER_ERROR);
        return error;
    }

    public static CustomError getErrorForParse(CustomError error) {
        error.setErrorCode(0);
        error.setErrorDetail(Constant.PARSE_ERROR);
        return error;
    }


    public static void CloseSource(Response response, HttpRequest request) {
        if (request.getResponseAs() != ResponseType.OK_HTTP_RESPONSE &&
                response != null && response.body() != null &&
                response.body().source() != null) {
            try {
                response.body().source().close();
            } catch (Exception ignore) {

            }
        }
    }

}
