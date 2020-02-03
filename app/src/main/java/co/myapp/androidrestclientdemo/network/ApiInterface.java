package co.myapp.androidrestclientdemo.network;

import java.util.ArrayList;

import co.myapp.androidrestclientdemo.model.Employee;
import co.myapp.androidrestclientdemo.model.EmployeeViewModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("/myreq/")
    Call<EmployeeViewModel> getEmployees();
    @POST("/myreq/")
    Call<Void> addEmployee(@Body Employee employee);
}
