package it.fulminazzo.chatapp.api.v1.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import it.fulminazzo.chatapp.api.v1.domain.entities.User
import it.fulminazzo.chatapp.api.v1.domain.requests.PrivateChatRequest
import it.fulminazzo.chatapp.api.v1.exceptions.HttpException
import it.fulminazzo.chatapp.api.v1.repositories.UserRepository
import it.fulminazzo.chatapp.api.v1.services.IPrivateChatService
import jakarta.servlet.ServletException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class PrivateChatControllerIntegrationTest extends Specification {
    @Autowired
    private PasswordEncoder encoder
    @Autowired
    private UserRepository userRepository
    @Autowired
    private IPrivateChatService chatService

    private ObjectMapper objectMapper
    private User firstUser
    private User secondUser
    private PrivateChatController controller
    private MockMvc mockMvc

    void setup() {
        objectMapper = new ObjectMapper()
        firstUser = userRepository.save(User.builder()
                .username('fulminazzo')
                .password(encoder.encode('password'))
                .build())
        secondUser = userRepository.save(User.builder()
                .username('felix')
                .password(encoder.encode('password'))
                .build())
        controller = new PrivateChatController(chatService)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def 'test that create chat returns ok'() {
        given:
        def userId = firstUser.id
        def otherUsername = secondUser.username

        and:
        def json = objectMapper.writeValueAsString(new PrivateChatRequest(otherUsername))

        when:
        def response = mockMvc.perform(
                MockMvcRequestBuilders.post('/api/v1/private/chats')
                        .requestAttr('userId', userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )

        then:
        response.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath('id').exists(),
                MockMvcResultMatchers.jsonPath('first.id').value(userId.toString()),
                MockMvcResultMatchers.jsonPath('second.id').value(secondUser.id.toString()),
        )
    }

    def 'test that create chat of already existing throws exception'() {
        given:
        def userId = firstUser.id
        def otherUsername = secondUser.username

        and:
        chatService.createChat(userId, otherUsername)

        and:
        def json = objectMapper.writeValueAsString(new PrivateChatRequest(otherUsername))

        when:
        mockMvc.perform(
                MockMvcRequestBuilders.post('/api/v1/private/chats')
                        .requestAttr('userId', userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )

        then:
        def e = thrown(ServletException)
        def actual = e.cause
        e.cause instanceof HttpException
        actual.httpStatus == HttpStatus.CONFLICT
    }

}

