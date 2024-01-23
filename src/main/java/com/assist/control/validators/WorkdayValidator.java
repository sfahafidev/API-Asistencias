package com.assist.control.validators;

import com.assist.control.domain.KindOfShift;
import com.assist.control.domain.Workday;
import com.assist.control.exceptions.BusinessRunTimeException;
import com.assist.control.exceptions.errors.Errors;
import com.assist.control.repository.KindOfShiftRepository;
import com.assist.control.repository.WorkdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
public class WorkdayValidator {

    private final WorkdayRepository workdayRepository;
    private final KindOfShiftRepository kindOfShiftRepository;
    private static final String REGULAR_SHIFT = "RS";
    private static final String OVERTIME = "O";
    private static final String DAY_OFF = "DO";
    private static final String VACATION = "V";

    @Autowired
    public WorkdayValidator(WorkdayRepository workdayRepository, KindOfShiftRepository kindOfShiftRepository) {
        this.workdayRepository = workdayRepository;
        this.kindOfShiftRepository = kindOfShiftRepository;
    }

    //TODO: validar que la hora de entrada y salida sean obligatorias cuando sean jornadas laborales

    public Double calculateTotalHours(LocalTime timeOfEntry, LocalTime timeOfExit){
        long totalSeconds = Duration.between(timeOfEntry, timeOfExit).getSeconds();
        double totalHours = (double)totalSeconds/3600;

        if (totalHours > 9) {
            throw new BusinessRunTimeException(Errors.REGULAR_SHIFT_ERROR_1);
        }else if (totalHours > 8) { // && totalHours <= 9 - revisar
            return totalHours - 1;
        }

        return totalHours;
    }

    public void validateTotalHoursWorkday(KindOfShift shift, double totalHoursPerDay){

        if (shift.getCode().equals(REGULAR_SHIFT) && (totalHoursPerDay < 6 || totalHoursPerDay > 8)) {
            throw new BusinessRunTimeException(Errors.REGULAR_SHIFT_ERROR_2);
        } else if (shift.getCode().equals(OVERTIME) && (totalHoursPerDay < 4 || totalHoursPerDay > 6)) {
            throw new BusinessRunTimeException(Errors.OVERTIME_ERROR);
        }
    }

    public KindOfShift findShift(String code){
        return kindOfShiftRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("El tipo de jornada indicado no existe"));
    }

    //TODO: reemplazar todas las RuntimeException. y agregar descripción al catalogo de errores

    public void validateShiftForDay(List<Workday> workdays, KindOfShift shift){

        if (!workdays.isEmpty()){
            workdays.forEach(x -> {
                if (!x.getShift().isWorking() && shift.isWorking() || x.getShift().isWorking() && !shift.isWorking()) {
                    throw new RuntimeException("No se puede cargar un " + shift.getDescriptionEs() + " con " + x.getShift().getDescriptionEs());
                } else if (x.getShift().equals(shift)){
                    throw new RuntimeException("Ya cargaste un " + x.getShift().getDescriptionEs() + " anteriormente");
                }
            });
        }
    }

    public void validateTotalHoursMixShift(List<Workday> workdays, KindOfShift shift, double totalHoursPerDay) {

        if (!workdays.isEmpty()){
            workdays.forEach(x -> {
                if (!x.getShift().isWorking() && !shift.isWorking()) {
                    throw new RuntimeException("No se puede cargar un vacaciones y dia libre el mismo dia");
                } else if ((x.getTotalHours() + totalHoursPerDay) > 12) {
                    throw new RuntimeException("Excediste el total de 12 horas por dia");
                }
            });
        }
    }

    public List<Workday> getCurrentDaysOfTheWeek(LocalDate today){
        LocalDate firstDayWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDayWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return workdayRepository.findByDateBetween(firstDayWeek, lastDayWeek);
    }

    public void calculateCurrentDaysOffWeek(List<Workday> workdaysOfCurrentWeek){
        List<Workday> daysOff = workdaysOfCurrentWeek.stream()
                .filter(x -> x.getShift().getCode().equals(DAY_OFF))
                .toList();

        if (daysOff.size() == 2) {
            throw new RuntimeException("No se pueden cargar mas de dos dias libres por semana");
        }
    }

    public void validateTotalHoursOfWeek(List<Workday> workdaysOfCurrentWeek, double totalHoursDay){
        double totalHoursWeek = 0;
        long totalShiftWeek = 0;

        if (!workdaysOfCurrentWeek.isEmpty()) {
            totalHoursWeek = getTotalHoursWeek(workdaysOfCurrentWeek);
            totalShiftWeek = getTotalShiftOfWeek(workdaysOfCurrentWeek);
        }

        if(totalShiftWeek >= 5 && (totalHoursWeek + totalHoursDay) > 48) {
            throw new RuntimeException("Excediste el total de 48 horas semanales");
        }

        if (totalShiftWeek >= 5 && (totalHoursWeek + totalHoursDay) < 30) {
            throw new RuntimeException("Debes cargar un mínimo de 30 horas por semana");
        }
    }

    public double getTotalHoursWeek(List<Workday> currentWeek){
        return currentWeek.stream()
                .filter(w -> w.getTotalHours() != null)
                .mapToDouble(Workday::getTotalHours)
                .sum();
    }

    public long getTotalShiftOfWeek(List<Workday> currentWeek){
        return currentWeek.stream()
                .filter(w -> w.getShift().isWorking())
                .count();
    }

}
