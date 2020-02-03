package co.myapp.androidrestclientdemo.Helper;

public interface AddEmployeeResponder {
    void onSuccess(Object response);
    void onFail(Throwable throwable);
}
