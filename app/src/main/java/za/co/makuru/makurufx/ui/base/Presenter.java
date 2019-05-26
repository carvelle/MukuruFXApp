package za.co.makuru.makurufx.ui.base;

public interface Presenter<V extends BaseView> {

    void onAttach(V view);

    void onDetach();
}
