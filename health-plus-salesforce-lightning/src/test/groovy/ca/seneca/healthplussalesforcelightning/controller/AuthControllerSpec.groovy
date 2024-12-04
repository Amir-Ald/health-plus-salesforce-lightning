package ca.seneca.healthplussalesforcelightning.controller

import ca.seneca.healthplussalesforcelightning.model.Users
import ca.seneca.healthplussalesforcelightning.service.AuthService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class AuthControllerSpec extends Specification {
    def authService = Mock(AuthService)
    def controller = new AuthController(authService: authService)
    MockMvc mockMvc

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "should login successfully"() {
        given:
        def email = "test@example.com"
        def password = "password123"
        def user = new Users(
            email: email,
            role: "MEMBER"
        )

        when:
        authService.login(email, password) >> Optional.of(user)
        def response = mockMvc.perform(post("/auth/login")
            .param("email", email)
            .param("password", password))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$.token').exists())
            .andExpect(jsonPath('$.role').value("MEMBER"))
    }

    def "should fail login with invalid credentials"() {
        given:
        def email = "wrong@example.com"
        def password = "wrongpass"

        when:
        authService.login(email, password) >> Optional.empty()
        def response = mockMvc.perform(post("/auth/login")
            .param("email", email)
            .param("password", password))

        then:
        response.andExpect(status().isUnauthorized())
            .andExpect(content().string("Invalid email or password"))
    }

    def "should get user roles"() {
        given:
        def userId = 1L
        def roles = ["MEMBER", "INSTRUCTOR"]

        when:
        authService.getRoles(userId) >> Optional.of(roles)
        def response = mockMvc.perform(get("/auth/roles")
            .param("userId", userId.toString()))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$[0]').value("MEMBER"))
            .andExpect(jsonPath('$[1]').value("INSTRUCTOR"))
    }

    def "should handle user not found when getting roles"() {
        given:
        def userId = 999L

        when:
        authService.getRoles(userId) >> Optional.empty()
        def response = mockMvc.perform(get("/auth/roles")
            .param("userId", userId.toString()))

        then:
        response.andExpect(status().isNotFound())
            .andExpect(content().string("User not found"))
    }
} 