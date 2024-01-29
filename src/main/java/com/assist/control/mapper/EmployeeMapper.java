package com.assist.control.mapper;

import com.assist.control.domain.Employee;
import com.assist.control.dto.request.RequestEmployeeDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING) // uses = {PaqueteMapper.class}
public interface EmployeeMapper {


    // @Mapping(source = "idVisita", ignore = true) para ignorar un campo

    /*@Mappings({
            @Mapping(source = "idVisita", target = "id"),
            @Mapping(source = "fechaLote", target = "fechaLote", dateFormat = "yyyy-MM-dd")

    })*/
    Employee ToEntity(RequestEmployeeDTO employeeDTO);

    @InheritInverseConfiguration
    RequestEmployeeDTO ToDto(Employee employee);

    List<Employee> dtoToEntityList(List<RequestEmployeeDTO> employeeDTOList);

}
