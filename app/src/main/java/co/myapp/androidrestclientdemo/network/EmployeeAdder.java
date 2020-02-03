package co.myapp.androidrestclientdemo.network;

import android.content.Context;
import android.util.Log;

import co.myapp.androidrestclientdemo.Helper.AddEmployeeResponder;
import co.myapp.androidrestclientdemo.model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployeeAdder {

    public static final String TAG = "EmployeeAdder";

    ApiInterface apiInterface;
    AddEmployeeResponder apiResponseListner;
    private Context context;
    private Retrofit retrofitClient;



    public EmployeeAdder(Context context){
        this.context = context;
    }

    public void setApiResponseListner(AddEmployeeResponder apiResponseListner) {
        this.apiResponseListner = apiResponseListner;
    }


    public void addEmployee(Employee employee){
        addRemoteEmployee(employee);
    }

    private void addRemoteEmployee(Employee employee){
        retrofitClient = ApiClient.getClient();
        apiInterface = retrofitClient.create(ApiInterface.class);
        Call<Void> call = apiInterface.addEmployee(employee);
       call.enqueue(new Callback<Void>() {
           @Override
           public void onResponse(Call<Void> call, Response<Void> response) {
               Log.d(TAG,"OnSuccess");
               if(apiResponseListner!=null){
                   apiResponseListner.onSuccess(response.body());
               }
           }

           @Override
           public void onFailure(Call<Void> call, Throwable t) {
               Log.d(TAG,"OnFail "+t);
               if(apiResponseListner!=null){
                   apiResponseListner.onFail(t);
               }
           }
       });
    }
}
