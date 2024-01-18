package com.assist.control.service;

import com.assist.control.domain.Employee;
import com.assist.control.domain.KindOfShift;
import com.assist.control.domain.Workday;
import com.assist.control.dto.request.RequestWorkdayDTO;
import com.assist.control.dto.request.RequestWorkdayFilterDTO;
import com.assist.control.dto.response.ResponseWorkdayDTO;
import com.assist.control.repository.WorkdayRepository;
import com.assist.control.service.interfaces.WorkdayInterface;
import com.assist.control.validators.EmployeeValidators;
import com.assist.control.validators.WorkdayValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkdayService implements WorkdayInterface {

    private final WorkdayRepository workdayRepository;
    private final EmployeeValidators employeeValidators;
    private final ModelMapper modelMapper;
    private final WorkdayValidator workdayValidator;

    @Autowired
    public WorkdayService(
            WorkdayRepository workdayRepository, EmployeeValidators employeeValidators,
            ModelMapper modelMapper, WorkdayValidator workdayValidator
    ) {
        this.workdayRepository = workdayRepository;
        this.employeeValidators = employeeValidators;
        this.modelMapper = modelMapper;
        this.workdayValidator = workdayValidator;
    }


    @Override
    public ResponseWorkdayDTO setWorkdayToEmployee(RequestWorkdayDTO request) {

        Employee employee = employeeValidators.validateEmployee(request.getIdEmployee());

        List<Workday> workdays = workdayRepository
                .findByEmployeeIdAndDate(request.getIdEmployee(), request.getDate());

        KindOfShift shift = workdayValidator.findShift(request.getKindOkShift());
        double totalHoursPerDay = workdayValidator.calculateTotalHours(request.getTimeOfArrival(), request.getDepartureTime());
        List<Workday> currentDaysOfTheWeek = workdayValidator.getWorkdaysOfCurrentWeek(request);

        workdayValidator.validateTotalHoursWorkday(request.getKindOkShift(), totalHoursPerDay);

        workdayValidator.validateWorkdaysForDay(workdays, request.getKindOkShift(), totalHoursPerDay);

        workdayValidator.calculateDaysOffCurrentWeek(currentDaysOfTheWeek);

        workdayValidator.validateTotalHoursOfWeek(currentDaysOfTheWeek, totalHoursPerDay);

        //Workday workday = modelMapper.map(request, Workday.class);
        Workday workday = RequestWorkdayDTO.workdayMap(request);
        workday.setTotalHours(totalHoursPerDay);
        workday.setApproved(false);
        workday.setEmployee(employee);
        workday.setShift(shift);

        workdayRepository.save(workday);

        ResponseWorkdayDTO response = modelMapper.map(workday, ResponseWorkdayDTO.class);
        response.setName(employee.getName());

        return response;
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
