package ca.seneca.healthplussalesforcelightning.service

import ca.seneca.healthplussalesforcelightning.model.Bookings
import ca.seneca.healthplussalesforcelightning.model.Classes
import ca.seneca.healthplussalesforcelightning.repository.BookingsRepository
import ca.seneca.healthplussalesforcelightning.repository.ClassesRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class ClassServiceSpec extends Specification {

    def classesRepository = Mock(ClassesRepository)
    def bookingsRepository = Mock(BookingsRepository)

    @Subject
    def classService = new ClassService(
        classesRepository: classesRepository,
        bookingsRepository: bookingsRepository
    )

    def "should return all classes"() {
        given:
        def testClass = new Classes(
            id: 1L,
            name: "Test Class",
            capacity: 10,
            startTime: LocalDateTime.now().plusDays(1),
            endTime: LocalDateTime.now().plusDays(1).plusHours(1)
        )

        when:
        classesRepository.findAll() >> [testClass]
        def result = classService.getAllClasses()

        then:
        result.size() == 1
        result[0].name == "Test Class"
    }

    def "should book a class and decrease capacity"() {
        given:
        def testClass = new Classes(
            id: 1L,
            name: "Test Class",
            capacity: 10,
            startTime: LocalDateTime.now().plusDays(1),
            endTime: LocalDateTime.now().plusDays(1).plusHours(1)
        )
        def booking = new Bookings(
            id: 1L,
            classEntity: testClass,
            status: "ACTIVE"
        )

        when:
        classesRepository.findById(1L) >> Optional.of(testClass)
        classesRepository.save(_) >> testClass
        bookingsRepository.save(_) >> booking
        def result = classService.bookClass(1L, booking)

        then:
        result == null
        testClass.capacity == 9
        1 * classesRepository.save(_)
        1 * bookingsRepository.save(_)
    }

    def "should throw exception when booking a full class"() {
        given:
        def testClass = new Classes(
            id: 1L,
            name: "Test Class",
            capacity: 0,
            startTime: LocalDateTime.now().plusDays(1),
            endTime: LocalDateTime.now().plusDays(1).plusHours(1)
        )
        def booking = new Bookings(
            classEntity: testClass,
            status: "ACTIVE"
        )

        when:
        classesRepository.findById(1L) >> Optional.of(testClass)
        classService.bookClass(1L, booking)

        then:
        thrown(RuntimeException)
        0 * classesRepository.save(_)
        0 * bookingsRepository.save(_)
    }

    def "should cancel booking and increase capacity"() {
        given:
        def testClass = new Classes(
            id: 1L,
            name: "Test Class",
            capacity: 9,
            startTime: LocalDateTime.now().plusDays(1),
            endTime: LocalDateTime.now().plusDays(1).plusHours(1)
        )
        def booking = new Bookings(
            id: 1L,
            classEntity: testClass,
            status: "ACTIVE"
        )

        when:
        bookingsRepository.findById(1L) >> Optional.of(booking)
        classesRepository.save(_) >> testClass
        bookingsRepository.save(_) >> booking
        def result = classService.cancelBooking(1L)

        then:
        result == null
        testClass.capacity == 10
        1 * classesRepository.save(_)
        1 * bookingsRepository.save(_)
    }
} 