package it.fulminazzo.chatapp.backend.security.utils

import io.jsonwebtoken.Jwts
import it.fulminazzo.chatapp.backend.security.config.JwtKeyConfig
import spock.lang.Specification

class JwtProviderTest extends Specification {

    private static final SECRET_KEY = 'SGVsbG8sIHdvcmxkISBUaGlzIGlzIGEgc3VwZXIgc2VjdXJlIG1lc3NhZ2UgdGhhdCBub2JvZHkgd2lsbCBiZSBhYmxlIHRvIGRlY29kZS4KVW5sZXNzIHlvdSBleHBsaWNpdGx5IHdhbnQgdG8gZ2V0IGhhY2tlZCwgeW91IHNob3VsZCBjaGFuZ2UgdGhpcyBrZXkgaW1tZWRpYXRlbHkuClBsZWFzZSwgRE8gTk9UIFVTRSBNRSBJTiBQUk9EVUNUSU9OLiBFVkVSLg'

    private JwtProvider provider

    void setup() {
        JwtKeyConfig config = Mock()
        config.secretKey >> SECRET_KEY
        provider = new JwtProvider(config)
    }

    def 'test that username extraction from jwt is successful'() {
        given:
        def token = generateToken()

        when:
        def username = provider.extractUsernameFromJwtToken(token)

        then:
        username == 'fulminazzo'
    }

    private String generateToken() {
        return Jwts.builder()
                .subject('fulminazzo')
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()))
                .signWith(provider.secretKey)
                .compact()
    }

}
