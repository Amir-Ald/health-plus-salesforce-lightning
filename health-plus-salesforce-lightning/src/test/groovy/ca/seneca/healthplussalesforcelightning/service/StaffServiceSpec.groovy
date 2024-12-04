package ca.seneca.healthplussalesforcelightning.service

import ca.seneca.healthplussalesforcelightning.model.Users
import ca.seneca.healthplussalesforcelightning.repository.UsersRepository
import spock.lang.Specification
import spock.lang.Subject

class StaffServiceSpec extends Specification {
    def usersRepository = Mock(UsersRepository)

    @Subject
    def staffService = new StaffService(usersRepository: usersRepository)

    def "should return all staff"() {
        given:
        def staff = new Users(
            id: 1L,
            name: "John Staff",
            email: "john@staff.com",
            role: "STAFF"
        )

        when:
        usersRepository.findAll() >> [staff]
        def result = staffService.getAllStaff()

        then:
        result.size() == 1
        result[0].name == "John Staff"
    }

    def "should return all instructors"() {
        given:
        def instructor = new Users(
            id: 1L,
            name: "John Instructor",
            email: "john@instructor.com",
            role: "INSTRUCTOR"
        )

        when:
        usersRepository.findByRoleIgnoreCase("INSTRUCTOR") >> [instructor]
        def result = staffService.getAllInstructors()

        then:
        result.size() == 1
        result[0].role == "INSTRUCTOR"
    }

    def "should add new staff member"() {
        given:
        def staff = new Users(
            name: "New Staff",
            email: "new@staff.com",
            role: "STAFF"
        )

        when:
        usersRepository.save(_) >> staff
        def result = staffService.addStaff(staff)

        then:
        result == null
        1 * usersRepository.save(_)
    }

    def "should update staff member"() {
        given:
        def staffId = 1L
        def staff = new Users(
            id: staffId,
            name: "Updated Staff",
            email: "updated@staff.com"
        )

        when:
        usersRepository.save(_) >> staff
        def result = staffService.updateStaff(staffId, staff)

        then:
        result == null
        1 * usersRepository.save(_)
    }
} 