package ca.seneca.healthplussalesforcelightning.service

import ca.seneca.healthplussalesforcelightning.model.Users
import ca.seneca.healthplussalesforcelightning.repository.UsersRepository
import spock.lang.Specification
import spock.lang.Subject

class AuthServiceSpec extends Specification {
    def usersRepository = Mock(UsersRepository)

    @Subject
    def authService = new AuthService(usersRepository: usersRepository)

    def "should login successfully with valid credentials"() {
        given:
        def email = "test@example.com"
        def password = "password123"
        def user = new Users(
            id: 1L,
            email: email,
            password: password,
            role: "MEMBER"
        )

        when:
        usersRepository.findByEmail(email) >> Optional.of(user)
        def result = authService.login(email, password)

        then:
        result.isPresent()
        result.get().email == email
        result.get().role == "MEMBER"
    }

    def "should fail login with invalid email"() {
        given:
        def email = "nonexistent@example.com"
        def password = "password123"

        when:
        usersRepository.findByEmail(email) >> Optional.empty()
        def result = authService.login(email, password)

        then:
        !result.isPresent()
    }

    def "should fail login with invalid password"() {
        given:
        def email = "test@example.com"
        def password = "wrongpassword"
        def user = new Users(
            email: email,
            password: "correctpassword",
            role: "MEMBER"
        )

        when:
        usersRepository.findByEmail(email) >> Optional.of(user)
        def result = authService.login(email, password)

        then:
        !result.isPresent()
    }

    def "should get roles for user"() {
        given:
        def userId = 1L
        def user = new Users(
            id: userId,
            role: "MEMBER"
        )

        when:
        usersRepository.findById(userId) >> Optional.of(user)
        def result = authService.getRoles(userId)

        then:
        result.isPresent()
        result.get() == ["MEMBER"]
    }

    def "should return empty when getting roles for non-existent user"() {
        given:
        def userId = 999L

        when:
        usersRepository.findById(userId) >> Optional.empty()
        def result = authService.getRoles(userId)

        then:
        !result.isPresent()
    }
} 