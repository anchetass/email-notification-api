package ffufm.shelly.api.utils

import de.ffuf.pass.common.security.introspector.pass4
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Order(1)
@EnableWebSecurity
class SBShellySecurity : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
//        TODO("Do this properly with our FFUF Access Manager Tool")
                 http
                    .authorizeRequests {
                        it.antMatchers("/actuator/**").permitAll()
                        it.anyRequest().permitAll()
                    }
                    .also {
                        it.cors().disable().csrf().disable()
                            .pass4()
                    }
    }
}
