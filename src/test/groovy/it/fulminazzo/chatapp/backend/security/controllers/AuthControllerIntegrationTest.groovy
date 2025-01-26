package it.fulminazzo.chatapp.backend.security.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import it.fulminazzo.chatapp.backend.security.objects.LoginRequest
import it.fulminazzo.chatapp.backend.security.services.IAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class AuthControllerIntegrationTest extends Specification {
    private ObjectMapper objectMapper
    @Autowired
    private IAuthenticationService service
    private AuthController controller
    private MockMvc mockMvc

    void setup() {
        this.objectMapper = new ObjectMapper()
        this.controller = new AuthController(service)
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def 'test that login of valid user returns ok'() {
        given:
        def request = new LoginRequest('fulminazzo', 'password')
        def json = objectMapper.writeValueAsString(request)

        when:
        def actual = mockMvc.perform(
                MockMvcRequestBuilders.post('/login')
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )

        then:
        actual.andExpect(
                MockMvcResultMatchers.status().isOk()
        )
    }

}
