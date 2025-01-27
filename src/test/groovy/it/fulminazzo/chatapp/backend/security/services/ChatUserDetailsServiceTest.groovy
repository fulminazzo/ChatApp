package it.fulminazzo.chatapp.backend.security.services

import it.fulminazzo.chatapp.backend.domain.entities.User
import it.fulminazzo.chatapp.backend.repositories.UserRepository
import it.fulminazzo.chatapp.backend.security.objects.ChatUserDetails
import jakarta.persistence.EntityExistsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class ChatUserDetailsServiceTest extends Specification {
    private User user
    private UserRepository userRepository
    private PasswordEncoder encoder
    private ChatUserDetailsService service

    void setup() {
        user = User.builder()
                .id(UUID.randomUUID())
                .username('fulminazzo')
                .password('password')
                .build()
        userRepository = Mock()
        userRepository.findByUsername(user.username) >> Optional.of(user)
        userRepository.findByUsername(_ as String) >> Optional.empty()
        encoder = new BCryptPasswordEncoder()
        service = new ChatUserDetailsService(userRepository, encoder)
    }

    def 'valid create user should call save from repository'() {
        given:
        def username = 'fulminazzobestfriend'
        def password = user.password

        when:
        def result = service.createUser(username, password)

        then:
        result.username == username
        new BCryptPasswordEncoder().matches(password, result.password)
        1 * userRepository.save(_ as User) >> { User u -> u }
    }

    def 'create user of already existing should throw exception'() {
        given:
        def username = user.username

        when:
        service.createUser(username, user.password)

        then:
        def e = thrown(EntityExistsException)
        e.message.contains(username)
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
