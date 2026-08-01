package com.nilemobile.backend.mapper;

import com.nilemobile.backend.dto.OrderDTO;
import com.nilemobile.backend.model.Order;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = OrderDetailMapper.class)
public interface OrderMapper {
    Order toEntity(OrderDTO orderDTO);

    @AfterMapping
    default void linkOrderDetails(@MappingTarget Order order) {
        order.getOrderDetails().forEach(orderDetail -> orderDetail.setOrder(order));
    }


    OrderDTO toDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order partialUpdate(OrderDTO orderDTO, @MappingTarget Order order);

    List<OrderDTO> toDtoList(List<Order> orders);
}