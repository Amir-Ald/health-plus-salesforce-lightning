package ca.seneca.healthplussalesforcelightning.service

import ca.seneca.healthplussalesforcelightning.model.Members
import ca.seneca.healthplussalesforcelightning.repository.MembersRepository
import spock.lang.Specification
import spock.lang.Subject

import java.sql.Date
import java.time.LocalDate

class MemberServiceSpec extends Specification {
    def membersRepository = Mock(MembersRepository)

    @Subject
    def memberService = new MemberService(membersRepository: membersRepository)

    def "should return all members"() {
        given:
        def member = new Members(
            id: 1L,
            name: "John Doe",
            contact: "123-456-7890",
            status: "ACTIVE",
            startDate: Date.valueOf(LocalDate.now()),
            endDate: Date.valueOf(LocalDate.now().plusMonths(1))
        )

        when:
        membersRepository.findAll() >> [member]
        def result = memberService.getAllMembers()

        then:
        result.size() == 1
        result[0].name == "John Doe"
    }

    def "should return active members"() {
        given:
        def member = new Members(status: "ACTIVE")

        when:
        membersRepository.findByStatusIgnoreCase("ACTIVE") >> [member]
        def result = memberService.getActiveMembers()

        then:
        result.size() == 1
        result[0].status == "ACTIVE"
    }

    def "should return inactive members"() {
        given:
        def member = new Members(status: "INACTIVE")

        when:
        membersRepository.findByStatusIgnoreCase("INACTIVE") >> [member]
        def result = memberService.getInactiveMembers()

        then:
        result.size() == 1
        result[0].status == "INACTIVE"
    }

    def "should return expiring memberships"() {
        given:
        def member = new Members(
            status: "ACTIVE",
            endDate: Date.valueOf(LocalDate.now().plusDays(15))
        )

        when:
        membersRepository.findByEndDateBetweenAndStatusIgnoreCase(_, _, _) >> [member]
        def result = memberService.getExpiringMemberships()

        then:
        result.size() == 1
        result[0].status == "ACTIVE"
    }
} 