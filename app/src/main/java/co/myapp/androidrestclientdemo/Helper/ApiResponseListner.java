package co.myapp.androidrestclientdemo.Helper;


import java.util.ArrayList;

import co.myapp.androidrestclientdemo.model.Employee;

public interface ApiResponseListner {
        public void onSuccess(ArrayList<Employee> data);
        public void onFail(int responseCode);
        public void onError(Throwable t);
    }


