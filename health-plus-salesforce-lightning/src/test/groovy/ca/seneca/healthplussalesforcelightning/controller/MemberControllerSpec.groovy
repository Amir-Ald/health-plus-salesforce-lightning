package ca.seneca.healthplussalesforcelightning.controller

import ca.seneca.healthplussalesforcelightning.model.Members
import ca.seneca.healthplussalesforcelightning.service.MemberService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

import java.sql.Date
import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class MemberControllerSpec extends Specification {
    def memberService = Mock(MemberService)
    def controller = new MemberController(memberService: memberService)
    MockMvc mockMvc
    ObjectMapper objectMapper

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
        objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JavaTimeModule())
    }

    def "should get all members"() {
        given:
        def member = new Members(
            id: 1L,
            name: "John Doe",
            contact: "123-456-7890",
            status: "ACTIVE"
        )

        when:
        memberService.getAllMembers() >> [member]
        def response = mockMvc.perform(get("/members"))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$[0].name').value("John Doe"))
    }

    def "should create new member"() {
        given:
        def member = new Members(
            name: "John Doe",
            contact: "123-456-7890",
            status: "ACTIVE"
        )

        when:
        memberService.addMember(_) >> member
        def response = mockMvc.perform(post("/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(member)))

        then:
        response.andExpect(status().isCreated())
            .andExpect(jsonPath('$.name').value("John Doe"))
    }

    def "should get active members"() {
        given:
        def member = new Members(status: "ACTIVE")

        when:
        memberService.getActiveMembers() >> [member]
        def response = mockMvc.perform(get("/members/active"))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$[0].status').value("ACTIVE"))
    }

    def "should get inactive members"() {
        given:
        def member = new Members(status: "INACTIVE")

        when:
        memberService.getInactiveMembers() >> [member]
        def response = mockMvc.perform(get("/members/inactive"))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$[0].status').value("INACTIVE"))
    }

    def "should get expiring memberships"() {
        given:
        def member = new Members(
            status: "ACTIVE",
            endDate: Date.valueOf(LocalDate.now().plusDays(15))
        )

        when:
        memberService.getExpiringMemberships() >> [member]
        def response = mockMvc.perform(get("/members/expiring-soon"))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$[0].status').value("ACTIVE"))
    }
} 