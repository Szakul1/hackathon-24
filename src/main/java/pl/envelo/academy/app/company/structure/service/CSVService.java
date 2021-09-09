package pl.envelo.academy.app.company.structure.service;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import pl.envelo.academy.app.company.structure.model.EmployeeModel;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVService {
    private final EmployeeService service;

    public CSVService(EmployeeService service) {
        this.service = service;
    }

    public void getFile(Writer writer){
        List<EmployeeModel> employeeModels = service.list();
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"Employee ID", "First Name", "Last Name", "E-mail", "Phone number", "Description",
                    "Supervisor ID"};
            String[] nameMapping = {"id", "name", "lastName", "email", "phoneNumber", "description", "supervisorId"};

            csvWriter.writeHeader(csvHeader);

            for (EmployeeModel employeeModel : employeeModels) {
                csvWriter.write(employeeModel, nameMapping);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
