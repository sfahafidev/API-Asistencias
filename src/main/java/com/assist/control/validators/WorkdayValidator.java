package com.assist.control.validators;

import com.assist.control.domain.KindOfShift;
import com.assist.control.domain.Workday;
import com.assist.control.exceptions.BusinessRunTimeException;
import com.assist.control.exceptions.errors.ApiError;
import com.assist.control.exceptions.errors.Errors;
import com.assist.control.repository.KindOfShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Component
public class WorkdayValidator {

    private final KindOfShiftRepository kindOfShiftRepository;
    private static final String REGULAR_SHIFT = "RS";
    private static final String OVERTIME = "O";
    private static final String DAY_OFF = "DO";

    @Autowired
    public WorkdayValidator(KindOfShiftRepository kindOfShiftRepository) {
        this.kindOfShiftRepository = kindOfShiftRepository;
    }

    public KindOfShift findShift(String code){
        return kindOfShiftRepository.findByCode(code)
                .orElseThrow(() -> new BusinessRunTimeException(Errors.TYPE_SHIFT_ERROR));
    }

    public void validateShiftForDay(List<Workday> workdays, KindOfShift shift){
        if (!workdays.isEmpty()){
            workdays.forEach(x -> {
                if (!x.getShift().isWorking() && shift.isWorking() || x.getShift().isWorking() && !shift.isWorking()) {

                    ApiError incompatibleShiftError = new ApiError
                            ("JornadasIncompatibles", "No se puede cargar " + shift.getDescriptionEs() + " con " + x.getShift().getDescriptionEs());
                    throw new BusinessRunTimeException(incompatibleShiftError);

                } else if (x.getShift().equals(shift)){

                    ApiError repeatedShiftError = new ApiError
                            ("JornadaYaExiste", "Ya cargaste " + x.getShift().getDescriptionEs() + " anteriormente");
                    throw new BusinessRunTimeException(repeatedShiftError);
                }
            });
        }
    }

    public void calculateCurrentDaysOffWeek(List<Workday> workdaysOfCurrentWeek) {
        List<Workday> daysOff = workdaysOfCurrentWeek.stream()
                .filter(x -> x.getShift().getCode().equals(DAY_OFF))
                .toList();

        if (daysOff.size() == 2) {
            throw new BusinessRunTimeException(Errors.TOTAL_DAYS_OFF_ERROR);
        }
    }

    public void validateArrivalDepartureWhenShiftIsWorking(LocalTime timeOfEntry, LocalTime timeOfExit){
        if (timeOfEntry == null || timeOfExit == null) {
            throw new BusinessRunTimeException(Errors.HOURS_SHIFT_WORKING_ERROR);
        }
    }

    public Double calculateTotalHours(LocalTime timeOfEntry, LocalTime timeOfExit) {
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

    public void validateTotalHoursMixShift(List<Workday> workdays, KindOfShift shift, double totalHoursPerDay) {
        if (!workdays.isEmpty()){
            workdays.forEach(x -> {
                if (!x.getShift().isWorking() && !shift.isWorking()) {

                    throw new BusinessRunTimeException(Errors.REPEATED_SHIFT_DAYS_OFF_ERROR);

                } else if ((x.getTotalHours() + totalHoursPerDay) > 12) {

                    throw new BusinessRunTimeException(Errors.TOTAL_HOURS_SHIFT_MIX_ERROR);
                }
            });
        }
    }

    public void validateTotalHoursOfWeek(List<Workday> workdaysOfCurrentWeek, double totalHoursDay) {
        double totalHoursWeek = 0;
        long totalShiftWeek = 0;

        if (!workdaysOfCurrentWeek.isEmpty()) {
            totalHoursWeek = getTotalHoursWeek(workdaysOfCurrentWeek);
            totalShiftWeek = getTotalShiftOfWeek(workdaysOfCurrentWeek);
        }

        if(totalShiftWeek >= 5 && (totalHoursWeek + totalHoursDay) > 48) {
            throw new BusinessRunTimeException(Errors.MAX_TOTAL_HOURS_WEEK);
        }

        if (totalShiftWeek >= 5 && (totalHoursWeek + totalHoursDay) < 30) {
            throw new BusinessRunTimeException(Errors.MIN_TOTAL_HOURS_WEEK);
        }
    }

    private double getTotalHoursWeek(List<Workday> currentWeek) {
        return currentWeek.stream()
                .filter(w -> w.getTotalHours() != null)
                .mapToDouble(Workday::getTotalHours)
                .sum();
    }

    private long getTotalShiftOfWeek(List<Workday> currentWeek) {
        return currentWeek.stream()
                .filter(w -> w.getShift().isWorking())
                .count();
    }

}
