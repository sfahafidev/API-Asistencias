package com.assist.control.service;

import com.assist.control.domain.Employee;
import com.assist.control.domain.KindOfShift;
import com.assist.control.domain.Workday;
import com.assist.control.dto.request.RequestWorkdayDTO;
import com.assist.control.dto.request.RequestWorkdayFilterDTO;
import com.assist.control.dto.response.ResponseWorkdayDTO;
import com.assist.control.exceptions.BusinessRunTimeException;
import com.assist.control.exceptions.errors.Errors;
import com.assist.control.repository.WorkdayRepository;
import com.assist.control.service.interfaces.WorkdayInterface;
import com.assist.control.validators.EmployeeValidators;
import com.assist.control.validators.WorkdayValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import static java.util.Objects.*;

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
    public ResponseWorkdayDTO addOrUpdateWorkdayToEmployee(RequestWorkdayDTO request) {

        Employee employee = employeeValidators.validateEmployee(request.getIdEmployee());

        List<Workday> workdays = workdayRepository
                .findByEmployeeIdAndDate(request.getIdEmployee(), request.getDate());

        KindOfShift shift = workdayValidator.findShift(request.getCodeShift());
        double totalHoursPerDay = 0;

        workdayValidator.validateShiftForDay(workdays, shift);

        Workday workday;
        List<Workday> currentDaysOfTheWeek = getCurrentDaysOfTheWeek(request.getDate());

        if (shift.isWorking()) {

            workdayValidator.validateArrivalDepartureWhenShiftIsWorking(request.getTimeOfArrival(), request.getDepartureTime());

            totalHoursPerDay = workdayValidator.calculateTotalHours(request.getTimeOfArrival(), request.getDepartureTime());

            workdayValidator.validateTotalHoursWorkday(shift, totalHoursPerDay);

            workdayValidator.validateTotalHoursMixShift(workdays, shift, totalHoursPerDay);

            workdayValidator.validateTotalHoursOfWeek(currentDaysOfTheWeek, totalHoursPerDay);

            if(isNull(request.getIdWorkday())){
                workday = Workday.isWorking(request);
            }else {
                workday = Workday.update(request);
            }

            workday.setTotalHours(totalHoursPerDay);

        }else {

            workdayValidator.calculateCurrentDaysOffWeek(currentDaysOfTheWeek);

            workday = Workday.nonWorking(request);
        }

        workday.setEmployee(employee);
        workday.setShift(shift);

        workdayRepository.save(workday);

        ResponseWorkdayDTO response = modelMapper.map(workday, ResponseWorkdayDTO.class);
        response.setName(employee.getName());
        response.setKindOfShift(workday.getShift().getDescriptionEs());

        return response;
    }

    private List<Workday> getCurrentDaysOfTheWeek(LocalDate today){
        LocalDate firstDayWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDayWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return workdayRepository.findByDateBetween(firstDayWeek, lastDayWeek);
    }

    @Override
    public void deleteWorkday(Long idWorkday) {
        Workday workday = workdayRepository.findById(idWorkday)
                .orElseThrow(() -> new BusinessRunTimeException(Errors.TYPE_SHIFT_ERROR));

        workdayRepository.delete(workday);
    }

    @Override
    public Workday findWorkday(Long idWorkday) {
        return null;
    }

    @Override
    public List<Workday> filterWorkdays(RequestWorkdayFilterDTO requestWorkdayFilter) {
        return null;
    }

    @Override
    public List<Workday> findWorkdaysByEmployee(Long idEmployee) {
        return workdayRepository.findByEmployeeId(idEmployee);
    }

}
