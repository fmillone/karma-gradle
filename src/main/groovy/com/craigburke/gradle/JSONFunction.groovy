package com.craigburke.gradle

import org.json.JSONString

class JSONFunction implements JSONString{

    String jsCode

    @Override
    String toJSONString() {
        return jsCode
    }
}
