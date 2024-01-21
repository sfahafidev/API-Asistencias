package com.assist.control.service.interfaces;

import com.assist.control.domain.Workday;
import com.assist.control.dto.request.RequestWorkdayDTO;
import com.assist.control.dto.request.RequestWorkdayFilterDTO;
import com.assist.control.dto.response.ResponseWorkdayDTO;

import java.util.List;

public interface WorkdayInterface {

    ResponseWorkdayDTO addWorkdayToEmployee(RequestWorkdayDTO requestWorkday);
    ResponseWorkdayDTO editWorkday(RequestWorkdayDTO requestWorkday);
    void deleteWorkday(Long idWorkday);
    Workday findWorkday(Long idWorkday);
    List<Workday> filterWorkdays(RequestWorkdayFilterDTO requestWorkdayFilter);
    List<Workday> findWorkdaysByEmployee(Long idEmployee);



}
