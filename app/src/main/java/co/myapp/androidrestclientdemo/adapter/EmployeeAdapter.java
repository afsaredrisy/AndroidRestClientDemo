package co.myapp.androidrestclientdemo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import co.myapp.androidrestclientdemo.MainActivity;
import co.myapp.androidrestclientdemo.R;
import co.myapp.androidrestclientdemo.model.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {


    private ArrayList<Employee> employees;

    public EmployeeAdapter(ArrayList<Employee> employees){
        this.employees=employees;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.employee_item,parent,false);
        int redom = new Random().nextInt();
        view.setBackgroundColor(redom);
        EmployeeViewHolder holder = new EmployeeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee=employees.get(position);
        holder.name.setText(employee.getFirstName()+" "+employee.getLastName());
        holder.email.setText(employee.getEmail());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder{

        private TextView name,email;
        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
        }
    }

}
