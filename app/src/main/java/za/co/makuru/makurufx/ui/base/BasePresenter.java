package za.co.makuru.makurufx.ui.base;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import za.co.makuru.makurufx.data.DataManager;
import za.co.makuru.makurufx.utils.rx.SchedulerProvider;

public class BasePresenter<V extends BaseView> implements Presenter<V> {

    private final DataManager mDataManager;
    private final CompositeDisposable mCompositeDisposable;
    private final SchedulerProvider mSchedulerProvider;

    private V mBaseView;

    @Inject
    public BasePresenter(DataManager dataManager,
                         CompositeDisposable compositeDisposable,
                         SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mCompositeDisposable = compositeDisposable;
        this.mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void onAttach(V baseView) {
        mBaseView = baseView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mBaseView = null;
    }

    public boolean isViewAttached() {
        return mBaseView != null;
    }

    public V getAttachedView() {
        return  mBaseView;
    }


    public void checkViewAttached() {
        if (!isViewAttached()) throw new ViewNotAttachedException();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public static class ViewNotAttachedException extends RuntimeException {
        public ViewNotAttachedException() {
            super("View Not Attached. Please call Presenter.onAttach(BaseView) before");
        }
    }
}


