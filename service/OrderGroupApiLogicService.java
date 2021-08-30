package com.koreait.day4.service;

import com.koreait.day4.model.entity.OrderGroup;
import com.koreait.day4.model.network.Header;
import com.koreait.day4.model.network.Pagination;
import com.koreait.day4.model.network.request.OrderGroupApiRequest;
import com.koreait.day4.model.network.response.OrderGroupApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {
    private final OrderGroupApiLogicService orderGroupApiLogicService;

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest orderGroupApiRequest = request.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                .orderType(orderGroupApiRequest.getOrderType())
                .status(orderGroupApiRequest.getStatus())
                .revAddress(orderGroupApiRequest.getRevAddress())
                .revName(orderGroupApiRequest.getRevName())
                .paymentType(orderGroupApiRequest.getPaymentType())
                .totalPrice(orderGroupApiRequest.getTotalPrice())
                .totalQuantity(orderGroupApiRequest.getTotalQuantity())
                .orderAt(orderGroupApiRequest.getOrderAt())
                .build();
        OrderGroup newOrderGroup = baseRepository.save(orderGroup);
        return Header.OK(response(newOrderGroup));
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(orderGroup -> response(orderGroup))
                .map(Header::OK)    // 찾았을 때 OK
                .orElseGet( // 데이터가 없다면 Header에 에러를 넣어서 보냄
                        () -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest orderGroupApiRequest = request.getData();
        Optional<OrderGroup> optional = baseRepository.findById(orderGroupApiRequest.getId());

        return optional.map(orderGroup -> {
            orderGroup.setOrderType(orderGroupApiRequest.getOrderType());
            orderGroup.setStatus(orderGroupApiRequest.getStatus());
            orderGroup.setRevAddress(orderGroupApiRequest.getRevAddress());
            orderGroup.setRevName(orderGroupApiRequest.getRevName());
            orderGroup.setPaymentType(orderGroupApiRequest.getPaymentType());
            orderGroup.setTotalPrice(orderGroupApiRequest.getTotalPrice());
            orderGroup.setTotalQuantity(orderGroupApiRequest.getTotalQuantity());
            orderGroup.setOrderAt(orderGroupApiRequest.getOrderAt());
            return orderGroup;
        }).map(orderGroup -> baseRepository.save(orderGroup))
                .map(orderGroup -> response(orderGroup))
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("찾는 데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        Optional<OrderGroup> optional = baseRepository.findById(id);
        return optional.map(orderGroup -> {
            baseRepository.delete(orderGroup);
            return Header.OK();     // Header에 OK를 보냄
        }).orElseGet(()->Header.ERROR("데이터 없음"));

    }

    private OrderGroupApiResponse response(OrderGroup orderGroup){
        OrderGroupApiResponse orderGroupApiResponse = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(orderGroup.getOrderType())
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getPaymentType())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
                .orderAt(orderGroup.getOrderAt())
                .arrivalDate(orderGroup.getArrivalDate())
                .userId(orderGroup.getUsers().getId())
                .build();
        return orderGroupApiResponse;
    }

    public Header<List<OrderGroupApiResponse>> search(Pageable pageable){
        Page<OrderGroup> orderGroup = baseRepository.findAll(pageable);
        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroup.stream()
                .map(orderGroups ->response(orderGroups))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(orderGroup.getTotalPages())
                .totalElements(orderGroup.getTotalElements())
                .currentPage(orderGroup.getNumber())
                .currentElements(orderGroup.getNumberOfElements())
                .build();
        return Header.OK(orderGroupApiResponseList, pagination);
    }
}
