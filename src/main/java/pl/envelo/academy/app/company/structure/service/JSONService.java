package pl.envelo.academy.app.company.structure.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.envelo.academy.app.company.structure.model.EmployeeModel;

import java.io.IOException;
import java.io.Writer;
import java.util.Optional;

public class JSONService {
    private EmployeeService service;

    public JSONService(EmployeeService service) {
        this.service = service;
    }

    public void getFile(Writer writer) {
        Optional<EmployeeModel> root = service.root();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        root.ifPresent(employeeModel -> gson.toJson(employeeModel, writer));
    }
}
