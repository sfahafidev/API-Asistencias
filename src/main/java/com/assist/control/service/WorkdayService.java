package com.assist.control.service;

import com.assist.control.domain.Employee;
import com.assist.control.domain.Workday;
import com.assist.control.dto.request.RequestWorkdayDTO;
import com.assist.control.dto.request.RequestWorkdayFilterDTO;
import com.assist.control.dto.response.ResponseWorkdayDTO;
import com.assist.control.repository.WorkdayRepository;
import com.assist.control.service.interfaces.WorkdayInterface;
import com.assist.control.validators.EmployeeValidators;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Service
public class WorkdayService implements WorkdayInterface {

    private final WorkdayRepository workdayRepository;
    private final EmployeeValidators employeeValidators;
    private final ModelMapper modelMapper;

    @Autowired
    public WorkdayService(
            WorkdayRepository workdayRepository, EmployeeValidators employeeValidators,
            ModelMapper modelMapper
    ) {
        this.workdayRepository = workdayRepository;
        this.employeeValidators = employeeValidators;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseWorkdayDTO setWorkdayToEmployee(RequestWorkdayDTO requestWorkday) {

        Employee employee = employeeValidators.validateEmployee(requestWorkday.getIdEmployee());

        //Workday workday = modelMapper.map(requestWorkday, Workday.class);
        Workday workday = RequestWorkdayDTO.workdayMap(requestWorkday);
        workday.setTotalHours(calculateTotalHours(requestWorkday.getTimeOfEntry(), requestWorkday.getTimeOfExit()));
        workday.setApproved(false);
        workday.setEmployee(employee);

        workdayRepository.save(workday);

        ResponseWorkdayDTO response = modelMapper.map(workday, ResponseWorkdayDTO.class);
        response.setName(employee.getName());

        return response;
    }

    private Double calculateTotalHours(LocalTime timeOfEntry, LocalTime timeOfExit){
        long totalSeconds = Duration.between(timeOfEntry, timeOfExit).getSeconds();
        double totalHours = (double)totalSeconds/3600;

        if (totalHours > 8){ return totalHours - 1; }

        return totalHours;
    }



    @Override
    public ResponseWorkdayDTO editWorkday(RequestWorkdayDTO requestWorkday) {
        return null;
    }

    @Override
    public void deleteWorkday(Long idWorkday) {

    }

    @Override
    public Workday findWorkday(Long idWorkday) {
        return null;
    }

    @Override
    public List<Workday> filterWorkdays(RequestWorkdayFilterDTO requestWorkdayFilte) {
        return null;
    }

    @Override
    public List<Workday> findWorkdaysByEmployee(Long idEmployee) {
        return workdayRepository.findByEmployeeId(idEmployee);
    }

}
