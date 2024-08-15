package az.edu.turing.msuser.controller;

import az.edu.turing.msuser.domain.enums.Gender;
import az.edu.turing.msuser.domain.enums.Status;
import az.edu.turing.msuser.domain.enums.Visibility;
import az.edu.turing.msuser.model.dto.ProfileDto;
import az.edu.turing.msuser.model.dto.UserDto;
import az.edu.turing.msuser.service.ProfileService;
import az.edu.turing.msuser.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserProfileController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ProfileService profileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addUser() throws Exception {
        UserDto mockUserDto = new UserDto(1L, "Jane", "Doe", "1234567", LocalDate.now(), LocalDate.now(), LocalDate.now(), null);

        when(userService.save(any(UserDto.class))).thenReturn(mockUserDto);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Jane"))
                .andExpect(jsonPath("$.lastname").value("Doe"));

        verify(userService, times(1)).save(any(UserDto.class));
    }

    @Test
    void testGetUserById() throws Exception {
        UserDto userDto = new UserDto(1L, "Jane", "Doe", "1234567", LocalDate.now(), LocalDate.now(), LocalDate.now(), null);

        when(userService.getById(1L)).thenReturn(Optional.of(userDto));

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Jane"))
                .andExpect(jsonPath("$.lastname").value("Doe"));

        verify(userService, times(1)).getById(1L);
    }

    @Test
    void testDeleteAllUsers() throws Exception {
        doNothing().when(userService).deleteAll();

        mockMvc.perform(delete("/api/v1/users/all"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteAll();
    }

    @Test
    void testDeleteUserById() throws Exception {
        doNothing().when(userService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateUserById() throws Exception {
        UserDto userDto = new UserDto(1L, "Jane", "Doe", "1234567", LocalDate.now(), LocalDate.now(), LocalDate.now(), null);

        when(userService.update(eq(1L), any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Jane"))
                .andExpect(jsonPath("$.lastname").value("Doe"));

        verify(userService, times(1)).update(eq(1L), any(UserDto.class));
    }

    @Test
    void testAddProfile() throws Exception {
        ProfileDto profileDto = createProfileDto();

        when(profileService.addProfile(eq(1L), any(ProfileDto.class))).thenReturn(profileDto);

        mockMvc.perform(post("/api/v1/users/1/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.phone_number").value("+994501234567"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.bio").value("Software Engineer with 5 years of experience."))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.image").value("https://example.com/images/john_doe.jpg"))
                .andExpect(jsonPath("$.user_id").value(10L))
                .andExpect(jsonPath("$.user_username").value("user123"))
                .andExpect(jsonPath("$.last_seen_visibility").value("PUBLIC"))
                .andExpect(jsonPath("$.image_visibility").value("PUBLIC"));

        verify(profileService, times(1)).addProfile(eq(1L), any(ProfileDto.class));
    }

    @Test
    void testGetProfileById() throws Exception {
        ProfileDto profileDto = createProfileDto();

        when(profileService.getProfileById(1L, 1L)).thenReturn(profileDto);

        mockMvc.perform(get("/api/v1/users/1/profiles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.phone_number").value("+994501234567"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.bio").value("Software Engineer with 5 years of experience."))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.image").value("https://example.com/images/john_doe.jpg"))
                .andExpect(jsonPath("$.user_id").value(10L))
                .andExpect(jsonPath("$.user_username").value("user123"))
                .andExpect(jsonPath("$.last_seen_visibility").value("PUBLIC"))
                .andExpect(jsonPath("$.image_visibility").value("PUBLIC"));

        verify(profileService, times(1)).getProfileById(1L, 1L);
    }

    @Test
    void testUpdateProfileById() throws Exception {
        ProfileDto profileDto = createProfileDto();

        when(profileService.updateProfile(eq(1L), eq(1L), any(ProfileDto.class))).thenReturn(profileDto);

        mockMvc.perform(put("/api/v1/users/1/profiles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.phone_number").value("+994501234567"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.bio").value("Software Engineer with 5 years of experience."))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.image").value("https://example.com/images/john_doe.jpg"))
                .andExpect(jsonPath("$.user_id").value(10L))
                .andExpect(jsonPath("$.user_username").value("user123"))
                .andExpect(jsonPath("$.last_seen_visibility").value("PUBLIC"))
                .andExpect(jsonPath("$.image_visibility").value("PUBLIC"));

        verify(profileService, times(1)).updateProfile(eq(1L), eq(1L), any(ProfileDto.class));
    }

    @Test
    void testDeleteProfileById() throws Exception {
        doNothing().when(profileService).deleteProfileById(1L, 1L);

        mockMvc.perform(delete("/api/v1/users/1/profiles/1"))
                .andExpect(status().isOk());

        verify(profileService, times(1)).deleteProfileById(1L, 1L);
    }

    private ProfileDto createProfileDto() {
        return new ProfileDto(
                1L,
                "john_doe",
                "123 Main St",
                "+994501234567",
                "john.doe@example.com",
                "Software Engineer with 5 years of experience.",
                Status.ACTIVE,
                Gender.MALE,
                "https://example.com/images/john_doe.jpg",
                10L,
                "user123",
                LocalDateTime.now(),
                Visibility.PUBLIC,
                Visibility.PUBLIC
        );
    }
}
