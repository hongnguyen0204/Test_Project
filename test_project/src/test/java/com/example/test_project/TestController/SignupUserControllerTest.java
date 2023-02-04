package com.example.test_project.TestController;

import com.example.test_project.Constant.URL;
import com.example.test_project.Controller.UserController;
import com.example.test_project.Models.RequestModels.SignupRequestModel;
import com.example.test_project.Service.UserService;
import com.example.test_project.TestProjectApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Map;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestProjectApplication.class)
@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class SignupUserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @ParameterizedTest(name = "#{index} - Run test with request = {0}")
    @MethodSource("invalidSignupRequest")
    public void testSignupUserError(Map<String, String> request) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(URL.API + URL.SIGN_UP)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest(name = "#{index} - Run test with request = {0}")
    @MethodSource("validSignupRequest")
    public void testSignupUserSuccess(Map<String, String> request) throws Exception {
        // Simulate return results
        when(userService.signup(any(SignupRequestModel.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mvc.perform(MockMvcRequestBuilders
                        .post(URL.API + URL.SIGN_UP)
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    static Stream<Arguments> validSignupRequest() {
        var TC1 = Map.of(
                "email", "hongnguyen0204@gmail.com",
                "password", "12345678",
                "firstName", "Nguyen",
                "lastName", "Hong"
        );

        return Stream.of(Arguments.of(TC1));
    }

    static Stream<Arguments> invalidSignupRequest() {
        var TC1 = Map.of(
                "email", "",
                "password", "12345678",
                "firstName", "test",
                "lastName", "test"
        );

        var TC2 = Map.of(
                "email", "abc",
                "password", "12345678",
                "firstName", "test",
                "lastName", "test"
        );

        var TC3 = Map.of(
                "email", "12341",
                "password", "12345678",
                "firstName", "test",
                "lastName", "test"
        );

        var TC4 = Map.of(
                "email", "abc.com",
                "password", "12345678",
                "firstName", "test",
                "lastName", "test"
        );

        var TC5 = Map.of(
                "email", "hong@gmail.com",
                "password", "",
                "firstName", "test",
                "lastName", "test"
        );

        var TC6 = Map.of(
                "email", "hong@gmail.com",
                "password", "1234567",
                "firstName", "test",
                "lastName", "test"
        );

        var TC7 = Map.of(
                "email", "hong@gmail.com",
                "password", "123456789123456789123",
                "firstName", "test",
                "lastName", "test"
        );

        return Stream.of(
                // Validation email
                Arguments.of(TC1), // empty email
                Arguments.of(TC2), // only character email
                Arguments.of(TC3), // only number email
                Arguments.of(TC4), // invalid format email

                // Validation password
                Arguments.of(TC5), // empty password
                Arguments.of(TC6), // min length password
                Arguments.of(TC7) // max length password
        );
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
