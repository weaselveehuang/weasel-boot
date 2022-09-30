package com.weasel

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class WeaselBootApplication {

    static void main(String[] args) {
        SpringApplication.run(WeaselBootApplication, args)
        ExpandoMetaClass.enableGlobally()
    }

}
