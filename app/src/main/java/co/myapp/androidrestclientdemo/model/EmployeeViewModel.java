package co.myapp.androidrestclientdemo.model;

import java.util.ArrayList;

public class EmployeeViewModel {
    private ArrayList<Employee> employeeList;

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
