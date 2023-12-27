package com.assist.control.service.interfaces;

import com.assist.control.dto.request.RequestWorkdayDTO;
import com.assist.control.dto.response.ResponseWorkdayDTO;

public interface WorkdayInterface {

    ResponseWorkdayDTO setWorkdayToEmployee(RequestWorkdayDTO requestWorkday);

}
