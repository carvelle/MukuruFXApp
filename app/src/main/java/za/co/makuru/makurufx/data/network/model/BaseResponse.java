package za.co.makuru.makurufx.data.network.model;

import okhttp3.Response;
import za.co.makuru.makurufx.data.network.error.CustomError;

public class BaseResponse<T>  {

        private final T mResult;

        private final CustomError mError;

        private Response response;

        public static <T> BaseResponse<T> success(T result) {
            return new BaseResponse<>(result);
        }

        public static <T> BaseResponse<T> failed(CustomError customError) {
            return new BaseResponse<>(customError);
        }

        public BaseResponse(T result) {
            this.mResult = result;
            this.mError = null;
        }

        public BaseResponse(CustomError customError) {
            this.mResult = null;
            this.mError = customError;
        }

        public T getResult() {
            return mResult;
        }

        public boolean isSuccess() {
            return mError == null;
        }

        public CustomError getError() {
            return mError;
        }

        public void setOkHttpResponse(Response response) {
            this.response = response;
        }

        public Response getOkHttpResponse() {
            return response;
        }
}
