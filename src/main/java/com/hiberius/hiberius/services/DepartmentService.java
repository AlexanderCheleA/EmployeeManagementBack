package com.hiberius.hiberius.services;

import com.hiberius.hiberius.dto.DepartmentDTO;
import com.hiberius.hiberius.dto.EmployeeDTO;
import com.hiberius.hiberius.mapper.DepartmentMapper;
import com.hiberius.hiberius.mapper.EmployeeMapper;
import com.hiberius.hiberius.models.DepartmentModel;
import com.hiberius.hiberius.models.EmployeeModel;
import com.hiberius.hiberius.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        DepartmentModel department = DepartmentMapper.INSTANCE.toEntity(dto);
        List<EmployeeDTO> employees = dto.getEmployees();
        if(employees != null && !employees.isEmpty()) {
            List<EmployeeModel> employeeModelList = EmployeeMapper.INSTANCE.toEntities(employees);
            department.setEmployees(employeeModelList);
        }

        DepartmentModel saved = departmentRepository.save(department);
        return DepartmentMapper.INSTANCE.toDTO(saved);
    }

    public void logicalDeleteDepartment(Long id) {
        departmentRepository.findById(id).ifPresent(dep -> {
            dep.setStatus("I");
            departmentRepository.save(dep);
        });
    }

    public List<DepartmentDTO> getDepartment() {
        List<DepartmentModel> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();

        if (departments != null && !departments.isEmpty()) {
            for (DepartmentModel departmentModel : departments) {
                if ("A".equals(departmentModel.getStatus()))  {
                    DepartmentDTO departmentDTO = new DepartmentDTO();
                    departmentDTO.setId(departmentModel.getId());
                    departmentDTO.setName(departmentModel.getName());

                    departmentDTOs.add(departmentDTO);
                }
            }
        }

        return departmentDTOs;
    }

    public List<DepartmentDTO> getDepartmentEmployee() {
        List<DepartmentModel> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOs = DepartmentMapper.INSTANCE.toDTOs(departments);

        return departmentDTOs;
    }

}
