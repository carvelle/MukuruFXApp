package za.co.makuru.makurufx.ui.base;

public interface BaseView {

    void showLoading();

    void hideLoading();

    void onError(String message);

    void hideKeyboard();

    void showMessage(String message);

}
