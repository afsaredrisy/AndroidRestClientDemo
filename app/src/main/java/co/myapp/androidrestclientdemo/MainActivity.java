package co.myapp.androidrestclientdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;

import co.myapp.androidrestclientdemo.Helper.AddEmployeeResponder;
import co.myapp.androidrestclientdemo.Helper.ApiResponseListner;
import co.myapp.androidrestclientdemo.Helper.RecyclerViewMargin;
import co.myapp.androidrestclientdemo.adapter.EmployeeAdapter;
import co.myapp.androidrestclientdemo.model.Employee;
import co.myapp.androidrestclientdemo.network.EmployeeAdder;
import co.myapp.androidrestclientdemo.network.EmployeeListFetcher;

public class MainActivity extends AppCompatActivity implements ApiResponseListner, AddEmployeeResponder {

    private static final String TAG ="MainActivity";
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ImageButton add;
    private EmployeeListFetcher employeeListFetcher;
    private EmployeeAdder employeeAdder;
    private ArrayList<Employee> employees;
    private EmployeeAdapter adapter;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progress_circular);
        recyclerView=findViewById(R.id.recycler);
        startFetch();
        add = findViewById(R.id.add);
    }
    private void startFetch(){
        progressBar.setVisibility(View.VISIBLE);
        employeeListFetcher = new EmployeeListFetcher(this);
        employeeListFetcher.setResponseListner(this);
        employeeListFetcher.fetch();
    }
    private void endFetch(){
        progressBar.setVisibility(View.GONE);
        if(employees!=null){
            adapter=new EmployeeAdapter(employees);
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
            ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            RecyclerViewMargin decoration = new RecyclerViewMargin(10, 1);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(adapter);
        }
        else {
            // No data available
        }
    }

    //Todo: List response
    @Override
    public void onSuccess(ArrayList<Employee> data) {
        Log.d(TAG,"onSuccess(ArrayList<Employee>)");
            Collections.reverse(data);
            this.employees=data;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    endFetch();
                }
            });
    }

    @Override
    public void onFail(int responseCode) {
        Log.d(TAG,"onFail(int)");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    endFetch();
                }
            });
    }

    @Override
    public void onError(Throwable t) {
        Log.d(TAG,"onError: "+t.getLocalizedMessage());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                endFetch();
            }
        });
    }

    public void addNewToList(View sender){

        if(sender.getId() == add.getId()){

            askDetails();
        }
    }

    public void askDetails(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.add_employee_dialog,null);
        final EditText id = layout.findViewById(R.id.idm),fname = layout.findViewById(R.id.fname)
                ,lName=layout.findViewById(R.id.lname),email = layout.findViewById(R.id.email);
        final Button addButton=layout.findViewById(R.id.addButton);
        final ProgressBar progressBar = layout.findViewById(R.id.adding);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(id,fname,lName,email)) {
                    addButton.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    addEmployee(new Employee(Integer.parseInt(id.getText().toString()),
                            fname.getText().toString(), lName.getText().toString(),
                            email.getText().toString()
                    ));
                }
            }
        });
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Add employee");
        alertDialog.setView(layout);
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = alertDialog.create();
        dialog.show();
    }

    private boolean validate(EditText id, EditText fName, EditText lName,EditText email){
        try{
            if(id.getText().toString().equals("")){
                showMessageAlert("Warning!","Enter employee id");
                return false;
            }
            if(fName.getText().toString().equals("") || lName.getText().toString().equals("")){
                showMessageAlert("Warning!","Enter employee Name ");
                return false;
            }
            if (email.getText().toString().equals("")){
                showMessageAlert("Warning!","Enter employee email");
                return false;
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public void addEmployee(Employee employee){
        employeeAdder=new EmployeeAdder(this);
        employeeAdder.setApiResponseListner(this);
        employeeAdder.addEmployee(employee);
    }
    //Todo: Add response
    @Override
    public void onSuccess(Object response) {
        Log.d(TAG,"onSuccess");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startFetch();
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });

    }

    @Override
    public void onFail(Throwable throwable) {
        Log.d(TAG,"onFail "+throwable);
    }
    private void showMessageAlert(String title , String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setCancelable(true);
        alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
