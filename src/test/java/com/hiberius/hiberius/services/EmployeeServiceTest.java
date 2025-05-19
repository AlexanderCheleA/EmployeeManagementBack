package com.hiberius.hiberius.services;


import com.hiberius.hiberius.dto.base.GlobalStatus;
import com.hiberius.hiberius.dto.request.EmployeeDTORq;
import com.hiberius.hiberius.dto.response.EmployeeDTORs;
import com.hiberius.hiberius.models.DepartmentModel;
import com.hiberius.hiberius.models.EmployeeModel;
import com.hiberius.hiberius.repository.DepartmentRepository;
import com.hiberius.hiberius.repository.EmployeeRepository;
import com.hiberius.hiberius.utils.MessagesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmployee_shouldThrowIfDepartmentNotFound() {
        EmployeeDTORq dto = new EmployeeDTORq();
        dto.setStatus(GlobalStatus.A);
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeService.createEmployee(dto, 1L));
        assertTrue(ex.getMessage().contains(String.format(MessagesUtil.Department.NOT_FOUND, 1L)));
    }

    @Test
    void createEmployee_shouldSetDefaultStatusAndSave() {
        EmployeeDTORq dto = new EmployeeDTORq();
        dto.setStatus(null);
        DepartmentModel dept = new DepartmentModel();
        dept.setId(1L);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(dept));
        when(employeeRepository.save(any(EmployeeModel.class))).thenAnswer(i -> i.getArguments()[0]);

        EmployeeDTORs result = employeeService.createEmployee(dto, 1L);

        assertNotNull(result);
        verify(employeeRepository, times(1)).save(any(EmployeeModel.class));
    }

    @Test
    void logicalDeleteEmployee_shouldThrowIfInvalidId() {
        RuntimeException ex1 = assertThrows(RuntimeException.class, () -> employeeService.logicalDeleteEmployee(null));
        assertEquals(MessagesUtil.Employee.INVALID_ID, ex1.getMessage());

        RuntimeException ex2 = assertThrows(RuntimeException.class, () -> employeeService.logicalDeleteEmployee(0L));
        assertEquals(MessagesUtil.Employee.INVALID_ID, ex2.getMessage());
    }

    @Test
    void logicalDeleteEmployee_shouldThrowIfNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeService.logicalDeleteEmployee(1L));
        assertTrue(ex.getMessage().contains(String.format(MessagesUtil.Department.NOT_FOUND, 1L)));
    }

    @Test
    void logicalDeleteEmployee_shouldThrowIfAlreadyInactive() {
        EmployeeModel employee = new EmployeeModel();
        employee.setStatus(GlobalStatus.I);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeService.logicalDeleteEmployee(1L));
        assertTrue(ex.getMessage().contains(String.format(MessagesUtil.Employee.ALREADY_INACTIVE, 1L)));
    }

    @Test
    void logicalDeleteEmployee_shouldUpdateStatusAndReturnDTO() {
        EmployeeModel employee = new EmployeeModel();
        employee.setStatus(GlobalStatus.A);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(EmployeeModel.class))).thenAnswer(i -> i.getArguments()[0]);

        EmployeeDTORs result = employeeService.logicalDeleteEmployee(1L);

        assertNotNull(result);
        assertEquals(GlobalStatus.I, employee.getStatus());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void getEmployeeWithHighestSalary_shouldReturnDTO() {
        EmployeeModel employee = new EmployeeModel();
        employee.setStatus(GlobalStatus.A);
        when(employeeRepository.findTopByStatusOrderBySalaryDesc(GlobalStatus.A)).thenReturn(Optional.of(employee));

        EmployeeDTORs result = employeeService.getEmployeeWithHighestSalary();

        assertNotNull(result);
    }

    @Test
    void getEmployeeWithHighestSalary_shouldThrowIfEmpty() {
        when(employeeRepository.findTopByStatusOrderBySalaryDesc(GlobalStatus.A)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeService.getEmployeeWithHighestSalary());
        assertEquals(MessagesUtil.Employee.ERROR_FUNDS, ex.getMessage());
    }

    @Test
    void getYoungestEmployee_shouldReturnDTO() {
        EmployeeModel employee = new EmployeeModel();
        employee.setStatus(GlobalStatus.A);
        when(employeeRepository.findTopByStatusOrderByAgeAsc(GlobalStatus.A)).thenReturn(Optional.of(employee));

        EmployeeDTORs result = employeeService.getYoungestEmployee();

        assertNotNull(result);
    }

    @Test
    void getYoungestEmployee_shouldThrowIfEmpty() {
        when(employeeRepository.findTopByStatusOrderByAgeAsc(GlobalStatus.A)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeService.getYoungestEmployee());
        assertEquals(MessagesUtil.Employee.ERROR_FUNDS, ex.getMessage());
    }

    @Test
    void countEmployeesJoinedLastMonth_shouldCallRepository() {
        LocalDate now = LocalDate.now();
        LocalDate startOfLastMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDate endOfLastMonth = startOfLastMonth.withDayOfMonth(startOfLastMonth.lengthOfMonth());

        when(employeeRepository.countByInitDateBetween(startOfLastMonth, endOfLastMonth)).thenReturn(5L);

        Long count = employeeService.countEmployeesJoinedLastMonth();

        assertEquals(5L, count);
    }

    @Test
    void getEmployee_shouldReturnAllEmployeesDTO() {
        EmployeeModel emp1 = new EmployeeModel();
        emp1.setStatus(GlobalStatus.A);
        EmployeeModel emp2 = new EmployeeModel();
        emp2.setStatus(GlobalStatus.A);
        when(employeeRepository.findAll()).thenReturn(List.of(emp1, emp2));

        List<EmployeeDTORs> result = employeeService.getEmployee();

        assertEquals(2, result.size());
    }

    @Test
    void getEmployee_shouldReturnEmptyListIfNoEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of());

        List<EmployeeDTORs> result = employeeService.getEmployee();

        assertTrue(result.isEmpty());
    }
}
