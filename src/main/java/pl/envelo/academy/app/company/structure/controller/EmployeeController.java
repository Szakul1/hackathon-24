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
import pl.envelo.academy.app.company.structure.service.CSVService;
import pl.envelo.academy.app.company.structure.service.EmployeeService;
import pl.envelo.academy.app.company.structure.service.JSONService;
import pl.envelo.academy.app.company.structure.service.PDFService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CSVService csvService;
    private final JSONService jsonService;
    private final PDFService pdfService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.csvService = new CSVService(employeeService);
        this.jsonService = new JSONService(employeeService);
        this.pdfService = new PDFService(employeeService);
    }

    @GetMapping
    public ResponseEntity<EmployeeModel> root() {
        Optional<EmployeeModel> employeeModels = employeeService.root();
        return ResponseEntity.of(employeeModels);
    }

    @PostMapping
    public ResponseEntity<EmployeeModel> create(@RequestBody EmployeeModel employee) {
        EmployeeModel created = employeeService.create(employee);
        return ResponseEntity.ok(created);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeModel> read(@PathVariable Long id) {
        EmployeeModel read = employeeService.read(id);
        return ResponseEntity.ok(read);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeModel> update(@PathVariable Long id, @RequestBody EmployeeModel employee) {
        EmployeeModel update = employeeService.update(id, employee);
        return ResponseEntity.ok(update);
    }

    @GetMapping(value = "/export/csv", produces = "text/csv; charset=utf-8")
    public void exportToCSV(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=employees.csv");
        try (PrintWriter writer = response.getWriter()){
            csvService.getFile(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/export/json", produces = "application/json")
    public void exportToJSON(HttpServletResponse response){
        response.setHeader("Content-Disposition", "attachment; filename=employees.json");
        try (PrintWriter writer = response.getWriter()){
            jsonService.getFile(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/export/pdf", produces = "application/pdf")
    public void exportToPDF(HttpServletResponse response){
        response.setHeader("Content-Disposition", "attachment; filename=employees.pdf");
        try (ServletOutputStream outputStream = response.getOutputStream()){
            pdfService.getFile(outputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }
}
