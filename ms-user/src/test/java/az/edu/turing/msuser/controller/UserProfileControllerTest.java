package az.edu.turing.msuser.controller;

import az.edu.turing.msuser.model.dto.UserDto;
import az.edu.turing.msuser.service.ProfileService;
import az.edu.turing.msuser.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.query.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserProfileController.class)
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ProfileService profileService;  // ProfileService üçün mock bean əlavə edin

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addUser() throws Exception {
        UserDto mockUserDto = new UserDto();
        mockUserDto.setId(1L);
        mockUserDto.setFirstname("Jane");
        mockUserDto.setLastname("Doe");

        when(userService.save(any(UserDto.class))).thenReturn(mockUserDto);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Jane"))
                .andExpect(jsonPath("$.lastname").value("Doe"));
    }

    @Test
    void getUsers() {
//    UserDto user1= new UserDto(1L,"Jane","Doe","1234567", LocalDate.now(),LocalDate.now(),LocalDate.now(),null);
//    UserDto user2 = new UserDto(1L,"Jane","Doe","1234567", LocalDate.now(),LocalDate.now(),LocalDate.now(),null);
//        List<UserDto> userDtoList= Arrays.asList(user1,user2);
//        Pageable pageable = PageRequest.of(0, 10); // 0-cı səhifə və 10 element
//
//// PageImpl obyektini yaradın
//        Page<UserDto> page = new PageImpl<>(userDtoList, pageable, userDtoList.size())

    }

    @Test
    void getUserById() {
    }

    @Test
    void deleteAllUsers() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void updateUserById() {
    }

    @Test
    void addProfile() {
    }

    @Test
    void getProfiles() {
    }

    @Test
    void getProfileById() {
    }

    @Test
    void deleteProfileById() {
    }

    @Test
    void updateProfileById() {
    }
}