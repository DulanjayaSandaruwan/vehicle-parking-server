package com.project.vehicle_parking.dto.user;

import com.project.vehicle_parking.commons.BaseRequest;
import com.project.vehicle_parking.model.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class UserSearchParamDTO extends BaseRequest {
    private String name;
    private String email;
    private User.UserStatus status;
    private int page;
    private int size;
}
