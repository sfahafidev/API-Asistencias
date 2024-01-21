package com.assist.control.validators;

import com.assist.control.domain.KindOfShift;
import com.assist.control.domain.Workday;
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
    private static final String REGULAR_SHIFT = "REGULAR_SHIFT";
    private static final String OVERTIME = "OVERTIME";
    private static final String DAY_OFF = "DAY_OFF";
    private static final String VACATION = "VACATION";

    @Autowired
    public WorkdayValidator(WorkdayRepository workdayRepository, KindOfShiftRepository kindOfShiftRepository) {
        this.workdayRepository = workdayRepository;
        this.kindOfShiftRepository = kindOfShiftRepository;
    }


    public Double calculateTotalHours(LocalTime timeOfEntry, LocalTime timeOfExit){
        long totalSeconds = Duration.between(timeOfEntry, timeOfExit).getSeconds();
        double totalHours = (double)totalSeconds/3600;

        if (totalHours > 8){ return totalHours - 1; }

        return totalHours;
    }

    public void validateTotalHoursWorkday(String kindOfWorkday, double totalHoursPerDay){
        KindOfShift shift = findShift(kindOfWorkday);

        if (shift.getCode().equals(REGULAR_SHIFT) && totalHoursPerDay < 6 || totalHoursPerDay > 8) {
            throw new RuntimeException("Las horas cargadas no coinciden con el turno normal");
        } else if (shift.getCode().equals(OVERTIME) && totalHoursPerDay < 4 || totalHoursPerDay > 6) {
            throw new RuntimeException("Las horas cargadas no coinciden con el turno extra");
        }
    }

    public KindOfShift findShift(String code){
        return kindOfShiftRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("El tipo de jornada indicado no existe"));
    }

    public void validateWorkdaysForDay(List<Workday> workdays, String kindOfWorkday, double totalHoursPerDay){
        KindOfShift shift = findShift(kindOfWorkday);

        if (!workdays.isEmpty()){
            workdays.forEach(x -> {
                if (!x.getShift().isWorking() && shift.isWorking() || x.getShift().isWorking() && !shift.isWorking()) {
                    throw new RuntimeException("No se puede cargar un " + shift.getDescription() + " con " + x.getShift().getDescription());
                } else if (x.getShift().equals(shift)){
                    throw new RuntimeException("Ya cargaste un " + x.getShift().getDescription() + " anteriormente");
                } else if (!x.getShift().isWorking() && !shift.isWorking()) {
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
                .filter(x -> x.getShift().getCode().equals(REGULAR_SHIFT))
                .toList();

        if (daysOff.size() == 2) {
            throw new RuntimeException("No se pueden cargar mas de dos dias libres por semana");
        }
    }

    public void validateTotalHoursOfWeek(List<Workday> workdaysOfCurrentWeek, double totalHoursDay){
        double totalHoursWeek = 0;
        if (!workdaysOfCurrentWeek.isEmpty()) {
            totalHoursWeek = getTotalHoursWeek(workdaysOfCurrentWeek);
        }

        if(workdaysOfCurrentWeek.size() == 4 && (totalHoursWeek + totalHoursDay) > 48) {
            throw new RuntimeException("Excediste el total de 48 horas semanales");
        }

        if (workdaysOfCurrentWeek.size() == 4 && (totalHoursWeek + totalHoursDay) < 30) {
            throw new RuntimeException("Debes cargar un mínimo de 30 horas por semana");
        }
    }

    public double getTotalHoursWeek(List<Workday> currentWeek){
        return currentWeek.stream()
                .filter(w -> w.getTotalHours() != null)
                .mapToDouble(Workday::getTotalHours)
                .sum();
    }

}
