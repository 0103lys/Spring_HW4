package com.koreait.day4.service;

import com.koreait.day4.model.entity.Users;
import com.koreait.day4.model.enumclass.UserStatus;
import com.koreait.day4.model.network.Header;
import com.koreait.day4.model.network.Pagination;
import com.koreait.day4.model.network.request.UserApiRequest;
import com.koreait.day4.model.network.response.UserApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service    // 서비스레이어, 내부에서 자바로직을 처리함
@RequiredArgsConstructor
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, Users> {

    // private final UserRepository userRepository; => 제거 : Entity를 가져오기 때문에
    private final OrderGroupApiLogicService orderGroupApiLogicService;
    private final ItemApiLogicService itemApiLogicService;
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) { // request에 정보가 들어옴
        UserApiRequest userApiRequest = request.getData();

        Users user = Users.builder()
                .userid(userApiRequest.getUserid())
                .userpw(userApiRequest.getUserpw())
                .hp(userApiRequest.getHp())
                .email(userApiRequest.getEmail())
                .status(UserStatus.REGISTERED)
                .build();
        Users newUser = baseRepository.save(user);
        return Header.OK(response(newUser));

    }

    @Override
    public Header<UserApiResponse> read(Long id) {  // id를 전달 받음
        return baseRepository.findById(id)
                .map(user -> response(user))    // 반복해서 돌림 user데이터를 받아서 response함수에 user를 넣음(user에 있는 데이터에서 id를 찾으면 response(user)에 저장)
                .map(Header::OK)    // 찾았을 때 OK
                .orElseGet( // 데이터가 없다면 Header에 에러를 넣어서 보냄
                        () -> Header.ERROR("데이터 없음")
                );

    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();
        Optional<Users> optional = baseRepository.findById(userApiRequest.getId()); // findById로 넘어온 데이터에서 id 찾아서 optional 객체를 만들고 저장

        return optional.map(user -> {   // 돌면서 user객체에서 데이터를 가져와서 set
                    user.setUserid(userApiRequest.getUserid()); // userApiRequest에서 전달 받은 값
                    user.setUserpw(userApiRequest.getUserpw());
                    user.setHp(userApiRequest.getHp());
                    user.setEmail(userApiRequest.getEmail());
                    user.setStatus(userApiRequest.getStatus());
                    return user; // user를 리턴
                }).map(user -> baseRepository.save(user)) // user를 받아서 repository에 save(user)하여 저장
                .map(user -> response(user))    // user객체를 받아서 response(user)쪽으로 전달
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));   // 데이터가 없으면 Header에 Error를 전달
    }


    @Override
    public Header delete(Long id) {     // Header<UserApiResponse> -> Header : 객체를 보내는 것이 아니라 OK만 보내줬기 때문에 Header객체에 전달하도록 함
        Optional<Users> optional = baseRepository.findById(id); // id를 찾아서 객체에 저장
        return optional.map(user-> {    // 돌면서 userRepository에서 user를 찾아서 삭제
            baseRepository.delete(user);
            return Header.OK();     // Header에 OK를 보냄
        }).orElseGet(()->Header.ERROR("데이터 없음"));   //  데이터가 없으면 Header에 Error를 전달

    }



    private UserApiResponse response(Users user){
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .userid(user.getUserid())
                .userpw(user.getUserpw())
                .hp(user.getHp())
                .email(user.getEmail())
                .regDate(user.getRegDate())
                .status(user.getStatus())
                .build();
        return userApiResponse;
    }


   /* public Header<UserOrderInfoApiResponse> orderInfo(Long id){
        Users user = baseRepository.getById(id);    // baseRepository 에서 id 찾기
        UserApiResponse userApiResponse = response(user);

        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResoponse> orderGroupApiResoponseList = orderGroupList.stream()  // stream 메소드를 사용
                .map(orderGroup -> {
                    OrderGroupApiResoponse orderGroupApiResoponse = orderGroupApiLogicService.response(orderGroup).getData();
                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                            .map(detail -> detail.getItem())
                            .map(item -> itemApiLogicService.response(item).getData())
                            .collect(Collectors.toList());  // collect 메소드는 stream의 데이터를 변형 등의 처리를 하고 원하는 자료형으로 변환

                    orderGroupApiResoponse.setItemApiResponseList(itemApiResponseList);
                    return orderGroupApiResoponse;
                }).collect(Collectors.toList());

        userApiResponse.setOrderGroupApiResoponseList(orderGroupApiResoponseList);
        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();
        return Header.OK(userOrderInfoApiResponse);

    }
*/
    public Header<List<UserApiResponse>> search(Pageable pageable){
        Page<Users> user = baseRepository.findAll(pageable);
        List<UserApiResponse> userApiResponseList = user.stream()
                .map(users ->response(users))
                .collect(Collectors.toList());
        Pagination pagination = Pagination.builder()
                .totalPages(user.getTotalPages())
                .totalElements(user.getTotalElements())
                .currentPage(user.getNumber())
                .currentElements(user.getNumberOfElements())
                .build();
        return Header.OK(userApiResponseList, pagination);
    }
}