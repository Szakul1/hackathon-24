package pl.envelo.academy.app.company.structure.service;

import org.springframework.stereotype.Service;
import pl.envelo.academy.app.company.structure.entity.EmployeeEntity;
import pl.envelo.academy.app.company.structure.mapper.EmployeeMapper;
import pl.envelo.academy.app.company.structure.model.EmployeeModel;
import pl.envelo.academy.app.company.structure.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeMapper employeeMapper, EmployeeRepository employeeRepository) {
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeModel> list() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        List<EmployeeModel> employeeModels = employeeMapper.toModel(employeeEntities);
        addSubordinates(employeeModels);
        return employeeModels;
    }

    public EmployeeModel create(EmployeeModel employeeModel) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeModel);
        EmployeeEntity created = employeeRepository.save(employeeEntity);
        EmployeeModel model = employeeMapper.toModel(created);
        addSubordinates(model);
        return model;
    }

    public EmployeeModel read(Long id) {
        EmployeeEntity read = employeeRepository.getById(id);
        EmployeeModel model = employeeMapper.toModel(read);
        addSubordinates(model);
        return model;
    }

    public EmployeeModel update(Long id, EmployeeModel employeeModel) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeModel);
        employeeEntity.setId(id);
        EmployeeEntity saved = employeeRepository.save(employeeEntity);
        return employeeMapper.toModel(saved);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    private void addSubordinates(EmployeeModel employeeModel) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeModel);
        List<EmployeeEntity> subordinatesEntity = employeeRepository.getBySupervisor(employeeEntity);
        List<EmployeeModel> subordinatesModel = employeeMapper.toModel(subordinatesEntity);
        addSubordinates(subordinatesModel);
        employeeModel.setSubordinates(subordinatesModel);
    }

    private void addSubordinates(List<EmployeeModel> models) {
        models.forEach(this::addSubordinates);
    }
}
