package com.example.employee_service.service.impl;

import com.example.employee_service.entity.DepartmentEntity;
import com.example.employee_service.entity.EmployeeEntity;
import com.example.employee_service.entity.UsersEntity;
import com.example.employee_service.payload.req.EmployeeReq;
import com.example.employee_service.payload.res.EmployeeRes;
import com.example.employee_service.payload.res.GlobalRes;
import com.example.employee_service.repository.DepartmentRepository;
import com.example.employee_service.repository.EmployeeRepository;
import com.example.employee_service.repository.UsersRepository;
import com.example.employee_service.service.EmployeeService;
import com.example.employee_service.utility.Message;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private final UsersRepository usersRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            UsersRepository usersRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public GlobalRes<List<EmployeeRes>> getAll() {
        try {
            List<EmployeeRes> list = employeeRepository.findAll()
                    .stream()
                    .map(this::toRes)
                    .collect(Collectors.toList());
            return new GlobalRes<>(true, Message.EMPLOYEE_FOUND, list);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<EmployeeRes> getById(Long id) {
        try {
            EmployeeEntity emp = employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Message.EMPLOYEE_NOT_FOUND));
            return new GlobalRes<>(true, Message.EMPLOYEE_FOUND, toRes(emp));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<EmployeeRes> create(EmployeeReq req) {
        try {
            if (employeeRepository.existsByNik(req.getNik())) {
                return new GlobalRes<>(false, Message.EMPLOYEE_EXISTS, null);
            }
            if (employeeRepository.existsByEmail(req.getEmail())) {
                return new GlobalRes<>(false, Message.EMPLOYEE_EXISTS, null);
            }

            DepartmentEntity dept = departmentRepository.findById(req.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException(Message.DEPARTMENT_NOT_FOUND));

            EmployeeEntity emp = new EmployeeEntity();
            emp.setNik(req.getNik());
            emp.setFullName(req.getFullName());
            emp.setEmail(req.getEmail());
            emp.setPhoneNumber(req.getPhoneNumber());
            emp.setAddress(req.getAddress());
            emp.setJoinDate(LocalDate.parse(req.getJoinDate()));
            emp.setDepartment(dept);

            if (req.getUserId() != null) {
                UsersEntity user = usersRepository.findById(req.getUserId())
                        .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
                emp.setUser(user);
            }

            employeeRepository.save(emp);

            return new GlobalRes<>(true, Message.EMPLOYEE_CREATED, toRes(emp));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<EmployeeRes> update(Long id, EmployeeReq req) {
        try {
            EmployeeEntity emp = employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Message.EMPLOYEE_NOT_FOUND));

            DepartmentEntity dept = departmentRepository.findById(req.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException(Message.DEPARTMENT_NOT_FOUND));

            emp.setFullName(req.getFullName());
            emp.setEmail(req.getEmail());
            emp.setPhoneNumber(req.getPhoneNumber());
            emp.setAddress(req.getAddress());
            emp.setJoinDate(LocalDate.parse(req.getJoinDate()));
            emp.setDepartment(dept);

            employeeRepository.save(emp);

            return new GlobalRes<>(true, Message.EMPLOYEE_UPDATED, toRes(emp));
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<String> delete(Long id) {
        try {
            EmployeeEntity emp = employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(Message.EMPLOYEE_NOT_FOUND));

            employeeRepository.delete(emp);

            return new GlobalRes<>(true, Message.EMPLOYEE_DELETED, null);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    @Override
    public GlobalRes<String> importFromExcel(MultipartFile file) {
        int successCount = 0;
        int failCount = 0;
        List<String> errors = new ArrayList<>();

        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Skip baris header
            if (rows.hasNext())
                rows.next();

            while (rows.hasNext()) {
                Row row = rows.next();
                try {
                    String nik = row.getCell(0).getStringCellValue().trim();
                    String fullName = row.getCell(1).getStringCellValue().trim();
                    String email = row.getCell(2).getStringCellValue().trim();
                    String phone = row.getCell(3).getStringCellValue().trim();
                    String address = row.getCell(4).getStringCellValue().trim();
                    String joinDate = row.getCell(5).getStringCellValue().trim();
                    Long deptId = (long) row.getCell(6).getNumericCellValue();

                    if (employeeRepository.existsByNik(nik) ||
                            employeeRepository.existsByEmail(email)) {
                        failCount++;
                        errors.add("Baris " + row.getRowNum() + ": NIK/email sudah terdaftar");
                        continue;
                    }

                    DepartmentEntity dept = departmentRepository.findById(deptId)
                            .orElseThrow(() -> new RuntimeException("Departemen tidak ditemukan"));

                    EmployeeEntity emp = new EmployeeEntity();
                    emp.setNik(nik);
                    emp.setFullName(fullName);
                    emp.setEmail(email);
                    emp.setPhoneNumber(phone);
                    emp.setAddress(address);
                    emp.setJoinDate(LocalDate.parse(joinDate));
                    emp.setDepartment(dept);

                    employeeRepository.save(emp);
                    successCount++;

                } catch (Exception e) {
                    failCount++;
                    errors.add("Baris " + row.getRowNum() + ": " + e.getMessage());
                }
            }

            workbook.close();

            String resultMsg = "Berhasil import " + successCount +
                    " data, gagal " + failCount + " data";
            if (!errors.isEmpty()) {
                resultMsg += ". Error: " + String.join(", ", errors);
            }

            return new GlobalRes<>(true, Message.EXCEL_SUCCESS, resultMsg);

        } catch (Exception e) {
            return new GlobalRes<>(false, Message.EXCEL_FAILED + ": " + e.getMessage(), null);
        }
    }

    // Helper: convert entity ke response
    private EmployeeRes toRes(EmployeeEntity emp) {
        EmployeeRes res = new EmployeeRes();
        res.setId(emp.getId());
        res.setNik(emp.getNik());
        res.setFullName(emp.getFullName());
        res.setEmail(emp.getEmail());
        res.setPhoneNumber(emp.getPhoneNumber());
        res.setAddress(emp.getAddress());
        res.setJoinDate(emp.getJoinDate() != null ? emp.getJoinDate().toString() : null);
        res.setDepartmentName(emp.getDepartment() != null ? emp.getDepartment().getDepartmentName() : null);
        res.setIsActive(emp.getIsActive());
        return res;
    }
}