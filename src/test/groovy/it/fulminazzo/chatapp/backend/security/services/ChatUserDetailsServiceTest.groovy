package it.fulminazzo.chatapp.backend.security.services

import it.fulminazzo.chatapp.backend.domain.entities.User
import it.fulminazzo.chatapp.backend.repositories.UserRepository
import it.fulminazzo.chatapp.backend.security.objects.ChatUserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class ChatUserDetailsServiceTest extends Specification {
    private def user
    private ChatUserDetailsService service

    void setup() {
        user = User.builder()
                .id(UUID.randomUUID())
                .username('fulminazzo')
                .password('password')
                .build()
        UserRepository userRepository = Mock()
        userRepository.findByUsername(user.username) >> Optional.of(user)
        userRepository.findByUsername(_) >> Optional.empty()
        service = new ChatUserDetailsService(userRepository, new BCryptPasswordEncoder())
    }

    def 'found user should return correct user details'() {
        given:
        def expected = new ChatUserDetails(user)

        when:
        def actual = service.loadUserByUsername(user.username)

        then:
        actual == expected
    }

    def 'not found user should throw exception with username'() {
        given:
        def username = 'fulminazzoeviltwin'

        when:
        service.loadUserByUsername(username)

        then:
        def e = thrown(UsernameNotFoundException)
        e.message == username
    }

}
