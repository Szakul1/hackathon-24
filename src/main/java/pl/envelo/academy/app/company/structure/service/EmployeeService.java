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

    public Optional<EmployeeModel> root() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findBySupervisor(null);
        if (employeeEntities.isEmpty())
            return Optional.empty();
        EmployeeEntity bossEntity = employeeEntities.get(0);
        EmployeeModel bossModel = employeeMapper.toModel(bossEntity);
        updateSubordinates(bossModel);
        return Optional.of(bossModel);
    }

    public List<EmployeeModel> list() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeMapper.toModel(employeeEntities);
    }

    public Optional<EmployeeModel> create(EmployeeModel employeeModel) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeModel);
        if (employeeModel.getSupervisorId() != null)
            updateSupervisor(employeeEntity, employeeModel.getSupervisorId());
        try {
            EmployeeEntity created = employeeRepository.save(employeeEntity);
            EmployeeModel model = employeeMapper.toModel(created);
            updateSubordinates(model);
            return Optional.of(model);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<EmployeeModel> read(Long id) {
        Optional<EmployeeEntity> read = employeeRepository.findById(id);
        if (read.isPresent()) {
            EmployeeModel model = employeeMapper.toModel(read.get());
            updateSubordinates(model);
            return Optional.of(model);
        }
        return Optional.empty();
    }

    public Optional<EmployeeModel> update(Long id, EmployeeModel employeeModel) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeModel);
        employeeEntity.setId(id);
        updateSupervisor(employeeEntity, employeeModel.getSupervisorId());
        try {
            EmployeeEntity saved = employeeRepository.save(employeeEntity);
            EmployeeModel model = employeeMapper.toModel(saved);
            return Optional.of(model);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    private void updateSubordinates(EmployeeModel employeeModel) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeModel);
        List<EmployeeEntity> subordinatesEntity = employeeRepository.findBySupervisor(employeeEntity);
        List<EmployeeModel> subordinatesModel = employeeMapper.toModel(subordinatesEntity);
        updateSubordinates(subordinatesModel);
        employeeModel.setSubordinates(subordinatesModel);
    }

    private void updateSupervisor(EmployeeEntity employeeEntity, Long supervisorId) {
        Optional<EmployeeEntity> supervisor = employeeRepository.findById(supervisorId);
        employeeEntity.setSupervisor(supervisor.get());
    }

    private void updateSubordinates(List<EmployeeModel> models) {
        models.forEach(this::updateSubordinates);
    }
}
