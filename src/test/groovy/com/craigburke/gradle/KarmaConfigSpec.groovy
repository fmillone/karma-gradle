package com.craigburke.gradle

import net.sf.json.JSONArray
import net.sf.json.JSONObject
import spock.lang.Unroll

import static com.craigburke.gradle.TestConstants.*

class KarmaConfigSpec extends KarmaBaseSpec {

    @Unroll('Reporter #reporter is added to config file')
    def "reporters are added to config file"() {
        karmaConfig.reporters = [reporter]
        karmaConfig.finalizeConfig()

        expect:
        configMap['reporters'] == [reporter] as JSONArray

        where:
        reporter << REPORTER_LIST
    }

    @Unroll('Browser #browser is added to config file')
    def "Browsers are added to config file"() {
        karmaConfig.browsers = [browser]
        karmaConfig.finalizeConfig()

        expect:
        configMap['browsers'] == [browser] as JSONArray

        where:
        browser << BROWSER_LIST
    }

    @Unroll('Framework #framework is added to config file')
    def "frameworks are added to config file"() {
        karmaConfig.frameworks = [framework]
        karmaConfig.finalizeConfig()

        expect:
        configMap['frameworks'] == [framework]  as JSONArray

        where:
        framework << FRAMEWORK_LIST
    }

    @Unroll('Add addition property #property')
    def "additional object properties added to DSL"() {
        when:
        karma {
            this[property] = value
        }

        and:
        karmaConfig.finalizeConfig()

        then:
        configMap[property] == value as JSONObject

        where:
        property      | value
        'mapProp'     | ['foo': ['bar': 999]]
        'emptyMapProp'     | [:]
    }

    @Unroll('Add addition property #property')
    def "additional list properties added to DSL"() {
        when:
            karma {
                this[property] = value
            }

        and:
            karmaConfig.finalizeConfig()

        then:
            configMap[property] == value as JSONArray

        where:
            property      | value
            'arrayProp'   | ['foo', 'bar', 'foobar']
            'emptyArrayProp'   | []
    }


    @Unroll('Add addition property #property')
    def "additional simple value property added to DSL"() {
        when:
            karma {
                this[property] = value
            }

        and:
            karmaConfig.finalizeConfig()

        then:
            configMap[property] == value

        where:
            property      | value
            'stringProp'  | 'bar'
            'booleanProp' | true

    }

    def "should add a string as value"(){
        when:
            karma {
                this['stringProp'] = new JSONFunction(jsCode:"function () {return 'hello world'; }")
            }
        and:
            karmaConfig.finalizeConfig()

        then:
            configMap['stringProp'] == "function(){ return 'hello world'; }"
    }


}
