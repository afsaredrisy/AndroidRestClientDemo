package co.myapp.androidrestclientdemo.network;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import co.myapp.androidrestclientdemo.Helper.ApiResponseListner;
import co.myapp.androidrestclientdemo.model.Employee;
import co.myapp.androidrestclientdemo.model.EmployeeViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployeeListFetcher {
    public static final String TAG = "EmployeeFetcher";

    ApiInterface apiInterface;
    ApiResponseListner apiResponseListner;
    private Context context;
    private Retrofit retrofitClient;


    public EmployeeListFetcher(Context context){
        this.context=context;
    }
    public void setResponseListner(ApiResponseListner responseListner) {
        this.apiResponseListner = responseListner;
    }
    public void fetch(){
        getEmployee();
    }


    private void getEmployee(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<EmployeeViewModel> call = apiInterface.getEmployees();
       call.enqueue(new Callback<EmployeeViewModel>() {
           @Override
           public void onResponse(Call<EmployeeViewModel> call, Response<EmployeeViewModel> response) {
               Log.d(TAG,"Sucess call "+response.body());
               if(apiResponseListner!=null){
                   apiResponseListner.onSuccess(response.body().getEmployeeList());
               }
           }

           @Override
           public void onFailure(Call<EmployeeViewModel> call, Throwable t) {
               Log.d(TAG,"Fail "+t);
               if(apiResponseListner!=null){
                   apiResponseListner.onError(t);
               }
           }
       });
    }


}
