package ca.seneca.healthplussalesforcelightning.controller

import ca.seneca.healthplussalesforcelightning.model.Users
import ca.seneca.healthplussalesforcelightning.service.StaffService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import com.fasterxml.jackson.databind.ObjectMapper

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class StaffControllerSpec extends Specification {
    def staffService = Mock(StaffService)
    def controller = new StaffController(staffService: staffService)
    MockMvc mockMvc
    ObjectMapper objectMapper

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
        objectMapper = new ObjectMapper()
    }

    def "should get all staff"() {
        given:
        def staff = new Users(
            id: 1L,
            name: "John Staff",
            email: "john@staff.com",
            role: "STAFF"
        )

        when:
        staffService.getAllStaff() >> [staff]
        def response = mockMvc.perform(get("/staff"))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$[0].name').value("John Staff"))
    }

    def "should get all instructors"() {
        given:
        def instructor = new Users(
            id: 1L,
            name: "John Instructor",
            role: "INSTRUCTOR"
        )

        when:
        staffService.getAllInstructors() >> [instructor]
        def response = mockMvc.perform(get("/staff/instructors"))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$[0].role').value("INSTRUCTOR"))
    }

    def "should create new staff member"() {
        given:
        def staff = new Users(
            name: "New Staff",
            email: "new@staff.com",
            role: "STAFF"
        )

        when:
        staffService.addStaff(_) >> staff
        def response = mockMvc.perform(post("/staff")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(staff)))

        then:
        response.andExpect(status().isCreated())
            .andExpect(jsonPath('$.name').value("New Staff"))
    }

    def "should update staff member"() {
        given:
        def staffId = 1L
        def staff = new Users(
            name: "Updated Staff",
            email: "updated@staff.com"
        )

        when:
        staffService.getStaffById(staffId) >> staff
        staffService.updateStaff(staffId, _) >> staff
        def response = mockMvc.perform(put("/staff/${staffId}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(staff)))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$.name').value("Updated Staff"))
    }

    def "should handle staff not found"() {
        given:
        def staffId = 999L
        def staff = new Users(name: "Not Found")

        when:
        staffService.getStaffById(staffId) >> null
        def response = mockMvc.perform(put("/staff/${staffId}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(staff)))

        then:
        response.andExpect(status().isNotFound())
            .andExpect(content().string("Staff member not found"))
    }
} 