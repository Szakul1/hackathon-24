package pl.envelo.academy.app.company.structure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.academy.app.company.structure.model.EmployeeModel;
import pl.envelo.academy.app.company.structure.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<EmployeeModel> list(){
        Optional<EmployeeModel> employeeModels = employeeService.list();
        return ResponseEntity.ok(employeeModels.orElse(null));
    }

    @PostMapping
    public ResponseEntity<EmployeeModel> create(@RequestBody EmployeeModel employee){
        EmployeeModel created = employeeService.create(employee);
        return ResponseEntity.ok(created);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeModel> read(@PathVariable Long id){
        EmployeeModel read = employeeService.read(id);
        return ResponseEntity.ok(read);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeModel> update(@PathVariable Long id, @RequestBody EmployeeModel employee) {
        EmployeeModel update = employeeService.update(id, employee);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        employeeService.delete(id);
    }
}
