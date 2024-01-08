package com.example.ordersystemconsumer.service;

import com.example.ordersystemconsumer.dto.OrderDTO;
import com.example.ordersystemconsumer.entity.ShipmentEntity;
import com.example.ordersystemconsumer.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    private final ModelMapper modelMapper = new ModelMapper();


    public Long saveOrder(OrderDTO orderDTO){
        ShipmentEntity shipmentEntity = ShipmentEntity.from(orderDTO);

        shipmentRepository.save(shipmentEntity);

        return shipmentEntity.getId();
    }
}
