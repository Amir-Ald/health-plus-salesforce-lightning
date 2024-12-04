package ca.seneca.healthplussalesforcelightning.controller

import ca.seneca.healthplussalesforcelightning.model.Bookings
import ca.seneca.healthplussalesforcelightning.model.Classes
import ca.seneca.healthplussalesforcelightning.service.ClassService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ClassControllerSpec extends Specification {

    def classService = Mock(ClassService)
    def controller = new ClassController(classService: classService)
    MockMvc mockMvc
    ObjectMapper objectMapper

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
        objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JavaTimeModule())
    }

    def "should get all classes"() {
        given:
        def testClass = new Classes(
            id: 1L,
            name: "Test Class",
            capacity: 10,
            startTime: LocalDateTime.now().plusDays(1),
            endTime: LocalDateTime.now().plusDays(1).plusHours(1)
        )

        when:
        classService.getAllClasses() >> [testClass]
        def response = mockMvc.perform(get("/classes"))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$[0].name').value("Test Class"))
            .andExpect(jsonPath('$[0].capacity').value(10))
    }

    def "should create a new class"() {
        given:
        def testClass = new Classes(
            name: "Test Class",
            capacity: 10,
            startTime: LocalDateTime.now().plusDays(1),
            endTime: LocalDateTime.now().plusDays(1).plusHours(1)
        )

        when:
        classService.addClass(_) >> testClass
        def response = mockMvc.perform(post("/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testClass)))

        then:
        response.andExpect(status().isCreated())
            .andExpect(jsonPath('$.name').value("Test Class"))
    }

    def "should book a class"() {
        given:
        def testClass = new Classes(
            id: 1L,
            name: "Test Class",
            capacity: 10
        )
        def booking = new Bookings(
            classEntity: testClass,
            status: "ACTIVE"
        )

        when:
        classService.getClassById(1L) >> testClass
        classService.bookClass(1L, _) >> booking
        def response = mockMvc.perform(post("/classes/1/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(booking)))

        then:
        response.andExpect(status().isCreated())
    }

    def "should cancel a booking"() {
        given:
        def booking = new Bookings(
            id: 1L,
            status: "ACTIVE"
        )

        when:
        classService.getBookingById(1L) >> booking
        classService.cancelBooking(1L) >> booking
        def response = mockMvc.perform(put("/classes/bookings/1/cancel"))

        then:
        response.andExpect(status().isOk())
    }

    def "should get upcoming classes"() {
        given:
        def testClass = new Classes(
            id: 1L,
            name: "Test Class",
            capacity: 10,
            startTime: LocalDateTime.now().plusDays(1),
            endTime: LocalDateTime.now().plusDays(1).plusHours(1)
        )

        when:
        classService.getUpcomingClasses() >> [testClass]
        def response = mockMvc.perform(get("/classes/upcoming"))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$[0].name').value("Test Class"))
    }

    def "should get all bookings"() {
        given:
        def testClass = new Classes(
            id: 1L,
            name: "Test Class"
        )
        def booking = new Bookings(
            id: 1L,
            classEntity: testClass,
            status: "ACTIVE"
        )

        when:
        classService.getAllBookings() >> [booking]
        def response = mockMvc.perform(get("/classes/bookings"))

        then:
        response.andExpect(status().isOk())
            .andExpect(jsonPath('$[0].status').value("ACTIVE"))
    }

    def "should handle class not found when booking"() {
        given:
        def booking = new Bookings(status: "ACTIVE")

        when:
        classService.getClassById(1L) >> null
        def response = mockMvc.perform(post("/classes/1/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(booking)))

        then:
        response.andExpect(status().isNotFound())
            .andExpect(content().string("Class not found"))
    }

    def "should handle full class when booking"() {
        given:
        def testClass = new Classes(
            id: 1L,
            capacity: 0
        )
        def booking = new Bookings(status: "ACTIVE")

        when:
        classService.getClassById(1L) >> testClass
        def response = mockMvc.perform(post("/classes/1/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(booking)))

        then:
        response.andExpect(status().isBadRequest())
            .andExpect(content().string("Class is full"))
    }
} 