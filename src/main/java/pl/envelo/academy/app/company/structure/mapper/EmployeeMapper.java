package pl.envelo.academy.app.company.structure.mapper;


import org.springframework.stereotype.Component;
import pl.envelo.academy.app.company.structure.entity.EmployeeEntity;
import pl.envelo.academy.app.company.structure.model.EmployeeModel;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeEntity toEntity(EmployeeModel employeeModel) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeModel.getId());
        employeeEntity.setName(employeeModel.getName());
        employeeEntity.setLastName(employeeModel.getLastName());
        employeeEntity.setEmail(employeeModel.getEmail());
        employeeEntity.setPhoneNumber(employeeModel.getPhoneNumber());
        employeeEntity.setDescription(employeeModel.getDescription());
        employeeEntity.setUrl(employeeModel.getUrl());
        return employeeEntity;
    }

    public EmployeeModel toModel(EmployeeEntity employeeEntity) {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setId(employeeEntity.getId());
        employeeModel.setName(employeeEntity.getName());
        employeeModel.setLastName(employeeEntity.getLastName());
        employeeModel.setEmail(employeeEntity.getEmail());
        employeeModel.setPhoneNumber(employeeEntity.getPhoneNumber());
        employeeModel.setDescription(employeeEntity.getDescription());
        employeeModel.setUrl(employeeEntity.getUrl());
        return employeeModel;
    }

    public List<EmployeeEntity> toEntity(List<EmployeeModel> employees) {
        return employees.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<EmployeeModel> toModel(List<EmployeeEntity> employees) {
        return employees.stream().map(this::toModel).collect(Collectors.toList());
    }
}
