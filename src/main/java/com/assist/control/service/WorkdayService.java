package com.assist.control.service;

import com.assist.control.domain.Employee;
import com.assist.control.domain.TypeWorkday;
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

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
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

        List<Workday> workdays = workdayRepository
                .findByEmployeeIdAndDate(requestWorkday.getIdEmployee(), requestWorkday.getDate());

        double totalHoursDay = calculateTotalHours(requestWorkday.getTimeOfEntry(), requestWorkday.getTimeOfExit());

        //  +++++++++++++++++++++++++++++++++++++++++++++++++

        String workdayType = requestWorkday.getType();
        if (!workdays.isEmpty()){
            workdays.forEach(x -> {
                if () {
                    throw new RuntimeException("Ya cargaste un " + x.getType() + " anteriormente");
                } else if () {
                    throw new RuntimeException("Excediste el total de 12 horas por dia");
                }
            });
        }


        // Verifico si hay jornadas repetidas en un día y si excede las horas por jornada +++++++++++++++

        if (!workdays.isEmpty()){
            workdays.forEach(x -> {
                if (x.getType().equals(requestWorkday.getType())){
                    throw new RuntimeException("Ya cargaste un " + x.getType() + " anteriormente");
                } else if ((x.getTotalHours() + totalHoursDay) > 12) {
                    throw new RuntimeException("Excediste el total de 12 horas por dia");
                }
            });
        } else if (workdayType.equals(TypeWorkday.TURNO_NORMAL.toString()) && totalHoursDay < 6 || totalHoursDay > 8) {
            throw new RuntimeException("Las horas cargadas no coincide con el tipo de jornada");
        } else if (workdayType.equals(TypeWorkday.TURNO_EXTRA.toString()) && totalHoursDay < 4 || totalHoursDay > 6) {
            throw new RuntimeException("Las horas cargadas no coincide con el tipo de jornada");
        }

        // verifico total de horas por semana +++++++++++++++++++++++++++++++++++++++++++++++++

        LocalDate today = requestWorkday.getDate();
        LocalDate firstDayWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDayWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<Workday> workdaysOfWeek = workdayRepository.findByDateBetween(firstDayWeek, lastDayWeek);


        double totalHoursWeek = 0;
        if (!workdaysOfWeek.isEmpty()) {
            totalHoursWeek = getTotalHoursWeek(workdaysOfWeek);
        }

        if(totalHoursWeek > 48) {
            throw new RuntimeException("Excediste el total de 48 horas semanales");
        }

        if (workdaysOfWeek.size() >= 5 && totalHoursWeek < 30) {
            throw new RuntimeException("Debes cargar un mínimo de 30 horas por semana");
        }

        //  +++++++++++++++++++++++++++++++++++++++++++++++++


        //Workday workday = modelMapper.map(requestWorkday, Workday.class);
        Workday workday = RequestWorkdayDTO.workdayMap(requestWorkday);
        workday.setTotalHours(totalHoursDay);
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

    private double getTotalHoursWeek(List<Workday> currentWeek){
        return currentWeek.stream()
                .filter(w -> w.getTotalHours() != null)
                .mapToDouble(Workday::getTotalHours)
                .sum();
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
