package com.hiberius.hiberius.services;

import com.hiberius.hiberius.dto.base.GlobalStatus;
import com.hiberius.hiberius.dto.request.DepartmentDTORq;
import com.hiberius.hiberius.dto.request.EmployeeDTORq;
import com.hiberius.hiberius.dto.response.DepartmentDTORs;
import com.hiberius.hiberius.mapper.request.DepartmentRequestMapper;
import com.hiberius.hiberius.models.DepartmentModel;
import com.hiberius.hiberius.repository.DepartmentRepository;
import com.hiberius.hiberius.utils.MessagesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDepartment_shouldThrowIfExists() {
        DepartmentDTORq dto = new DepartmentDTORq();
        dto.setName("IT");
        dto.setStatus(GlobalStatus.A);

        DepartmentModel departmentModel = DepartmentRequestMapper.INSTANCE.toEntity(dto);
        when(departmentRepository.findByNameAndStatus(departmentModel.getName(), departmentModel.getStatus()))
                .thenReturn(List.of(departmentModel));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> departmentService.createDepartment(dto));
        assertTrue(ex.getMessage().contains(MessagesUtil.Department.ERROR_EXISTS.split("%s")[0]));
    }

    @Test
    void createDepartment_shouldSaveAndReturn() {
        DepartmentDTORq dto = new DepartmentDTORq();
        dto.setName("HR");
        dto.setStatus(GlobalStatus.A);
        dto.setEmployees(List.of(new EmployeeDTORq()));

        DepartmentModel departmentModel = DepartmentRequestMapper.INSTANCE.toEntity(dto);
        when(departmentRepository.findByNameAndStatus(departmentModel.getName(), departmentModel.getStatus()))
                .thenReturn(List.of());
        when(departmentRepository.save(any(DepartmentModel.class))).thenAnswer(i -> i.getArguments()[0]);

        DepartmentDTORs result = departmentService.createDepartment(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        verify(departmentRepository, times(1)).save(any(DepartmentModel.class));
    }

    @Test
    void logicalDeleteDepartment_shouldThrowIfInvalidId() {
        RuntimeException ex1 = assertThrows(RuntimeException.class, () -> departmentService.logicalDeleteDepartment(null));
        assertEquals(MessagesUtil.Department.INVALID_ID, ex1.getMessage());

        RuntimeException ex2 = assertThrows(RuntimeException.class, () -> departmentService.logicalDeleteDepartment(0L));
        assertEquals(MessagesUtil.Department.INVALID_ID, ex2.getMessage());
    }

    @Test
    void logicalDeleteDepartment_shouldThrowIfNotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> departmentService.logicalDeleteDepartment(1L));
        assertTrue(ex.getMessage().contains(String.format(MessagesUtil.Department.NOT_FOUND, 1L)));
    }

    @Test
    void logicalDeleteDepartment_shouldThrowIfAlreadyInactive() {
        DepartmentModel department = new DepartmentModel();
        department.setStatus(GlobalStatus.I);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> departmentService.logicalDeleteDepartment(1L));
        assertTrue(ex.getMessage().contains(String.format(MessagesUtil.Department.ALREADY_INACTIVE, 1L)));
    }

    @Test
    void logicalDeleteDepartment_shouldUpdateStatusAndReturnDTO() {
        DepartmentModel updated = new DepartmentModel();
        updated.setStatus(GlobalStatus.I);

        DepartmentModel department = new DepartmentModel();
        department.setStatus(GlobalStatus.A);
        department.setName("Finance");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any())).thenReturn(updated);

        DepartmentDTORs result = departmentService.logicalDeleteDepartment(1L);

        assertNotNull(result);
        assertEquals(GlobalStatus.I.getDescripcion(), result.getStatus());
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void getDepartment_shouldReturnOnlyActiveDepartments() {
        DepartmentModel activeDept = new DepartmentModel();
        activeDept.setStatus(GlobalStatus.A);
        activeDept.setName("ActiveDept");
        DepartmentModel inactiveDept = new DepartmentModel();
        inactiveDept.setStatus(GlobalStatus.I);
        inactiveDept.setName("InactiveDept");

        when(departmentRepository.findAll()).thenReturn(List.of(activeDept, inactiveDept));

        List<DepartmentDTORs> result = departmentService.getDepartment();

        assertEquals(1, result.size());
        assertEquals("ActiveDept", result.get(0).getName());
    }

    @Test
    void getDepartmentEmployee_shouldReturnAllDepartmentsDTO() {
        DepartmentModel dept1 = new DepartmentModel();
        dept1.setStatus(GlobalStatus.A);
        DepartmentModel dept2 = new DepartmentModel();
        dept2.setStatus(GlobalStatus.A);
        when(departmentRepository.findAll()).thenReturn(List.of(dept1, dept2));

        List<DepartmentDTORs> result = departmentService.getDepartmentEmployee();

        assertEquals(2, result.size());
    }
}