package za.co.makuru.makurufx.data.network.error;

import okhttp3.Response;
import za.co.makuru.makurufx.constants.Constant;
import za.co.makuru.makurufx.utils.ParseUtil;

public class CustomError extends Exception {

    private String errorBody;

    private int errorCode = 0;

    private String errorDetail;

    private Response response;

    public CustomError() {
    }

    public CustomError(Response response) {
        this.response = response;
    }

    public CustomError(String message) {
        super(message);
    }

    public CustomError(String message, Response response) {
        super(message);
        this.response = response;
    }

    public CustomError(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CustomError(String message, Response response, Throwable throwable) {
        super(message, throwable);
        this.response = response;
    }

    public CustomError(Response response, Throwable throwable) {
        super(throwable);
        this.response = response;
    }

    public CustomError(Throwable throwable) {
        super(throwable);
    }

    public Response getResponse() {
        return response;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public String getErrorDetail() {
        return this.errorDetail;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setCancellationMessageInError() {
        this.errorDetail = Constant.REQUEST_CANCELLED_ERROR;
    }

    public String getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(String errorBody) {
        this.errorBody = errorBody;
    }

    public <T> T getErrorAsObject(Class<T> objectClass) {
        try {
            return (T) (ParseUtil
                    .getParserFactory()
                    .getObject(errorBody, objectClass));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
