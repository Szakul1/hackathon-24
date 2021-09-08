package pl.envelo.academy.app.company.structure.service;

import org.springframework.stereotype.Service;
import pl.envelo.academy.app.company.structure.entity.EmployeeEntity;
import pl.envelo.academy.app.company.structure.mapper.EmployeeMapper;
import pl.envelo.academy.app.company.structure.model.EmployeeModel;
import pl.envelo.academy.app.company.structure.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeMapper employeeMapper, EmployeeRepository employeeRepository) {
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
    }

    public Optional<EmployeeModel> list() {
        List<EmployeeEntity> employeeEntities = employeeRepository.getBySupervisor(null);
        if (employeeEntities.isEmpty())
            return Optional.empty();
        EmployeeEntity bossEntity = employeeEntities.get(0);
        EmployeeModel bossModel = employeeMapper.toModel(bossEntity);
        updateSubordinates(bossModel);
        return Optional.of(bossModel);
    }

    public EmployeeModel create(EmployeeModel employeeModel) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeModel);
        updateSupervisor(employeeEntity, employeeModel.getSupervisorId());
        EmployeeEntity created = employeeRepository.save(employeeEntity);
        EmployeeModel model = employeeMapper.toModel(created);
        updateSubordinates(model);
        return model;
    }

    public EmployeeModel read(Long id) {
        EmployeeEntity read = employeeRepository.getById(id);
        EmployeeModel model = employeeMapper.toModel(read);
        updateSubordinates(model);
        return model;
    }

    public EmployeeModel update(Long id, EmployeeModel employeeModel) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeModel);
        employeeEntity.setId(id);
        updateSupervisor(employeeEntity, employeeModel.getSupervisorId());
        EmployeeEntity saved = employeeRepository.save(employeeEntity);
        return employeeMapper.toModel(saved);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    private void updateSubordinates(EmployeeModel employeeModel) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeModel);
        List<EmployeeEntity> subordinatesEntity = employeeRepository.getBySupervisor(employeeEntity);
        List<EmployeeModel> subordinatesModel = employeeMapper.toModel(subordinatesEntity);
        updateSubordinates(subordinatesModel);
        employeeModel.setSubordinates(subordinatesModel);
    }

    private void updateSupervisor(EmployeeEntity employeeEntity, Long supervisorId){
        EmployeeEntity supervisor = employeeRepository.getById(supervisorId);
        employeeEntity.setSupervisor(supervisor);
    }

    private void updateSubordinates(List<EmployeeModel> models) {
        models.forEach(this::updateSubordinates);
    }
}
