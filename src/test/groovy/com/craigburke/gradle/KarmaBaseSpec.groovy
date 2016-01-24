package com.craigburke.gradle

import net.sf.json.JSONObject
import spock.lang.Shared
import spock.lang.Specification

class KarmaBaseSpec extends Specification {

    @Shared
    KarmaModuleExtension karmaConfig

    def setup() {
        karmaConfig = new KarmaModuleExtension()
    }

    protected void karma(Closure closure) {
        closure.rehydrate(karmaConfig, karmaConfig, karmaConfig).call()
    }

    protected getConfigMap() {
       JSONObject.fromObject( karmaConfig.configJson );
    }

    protected Profile getProfile(String name) {
        Profile profile = KarmaConstants.PROFILES[name].clone()
        profile.setDefaults()
        profile
    }
}
