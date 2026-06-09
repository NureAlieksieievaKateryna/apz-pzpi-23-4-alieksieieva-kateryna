package org.example.reminder.common.mappers;

import lombok.RequiredArgsConstructor;
import org.example.reminder.user.dto.UserResponse;
import org.example.reminder.user.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponse toUserDto(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .email(userEntity.getEmail())
                .build();
    }
}
