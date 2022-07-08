package com.example.SpringReactBookstore.services;

import com.example.SpringReactBookstore.models.User;
import com.example.SpringReactBookstore.models.UserDTO;
import com.example.SpringReactBookstore.repositories.UserDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserDAO userDAO;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    public void shouldReturnUserIdWhenCalledWithUserData(){

        UUID id = UUID.randomUUID();

        when(userDAO.saveAndFlush(any())).thenReturn(getUser(id));

        when(modelMapper.map(any(), any())).thenReturn(getUser(id));

        UUID user = userService.addUser(getUserDTO());

        assertThat(user).isNotNull();

        assertThat(user).isEqualTo(id);

    }

    @Test
    public void shouldReturnUserWhenEmailIsSent(){

        UUID id = UUID.randomUUID();

        when(userDAO.findByEmail(anyString())).thenReturn(getUser(id));

        when(modelMapper.map(any(), any())).thenReturn(getUserDTO());

        UserDTO user = userService.getUserByEmail("bob@email.com");

        assertThat(user).isNotNull();

        assertThat(user.getName()).isEqualTo("Bob");
    }

    @Test
    public void shouldThrowErrorWhenEmailDoesNotExist(){

        when(userDAO.findByEmail(anyString())).thenThrow(new RuntimeException("user doesn't exist with that email"));

        assertThatThrownBy(() -> userService.getUserByEmail("bob@gmail.com")).isInstanceOf(RuntimeException.class);
    }

    private UserDTO getUserDTO() {

        return UserDTO.builder()
                .id(UUID.randomUUID())
                .name("Bob")
                .email("bob@gmail.com")
                .password("password")
                .build();
    }

    private User getUser(UUID id) {

        return User.builder()
                .id(id)
                .name("Bob")
                .email("bob@email.com")
                .password("password")
                .build();
    }
}