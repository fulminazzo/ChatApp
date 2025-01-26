package it.fulminazzo.chatapp

import spock.lang.Specification

class SpockTest extends Specification {

    def 'test spock is functioning'() {
        expect:
        !'Hello from Spock!'.empty
    }

}
