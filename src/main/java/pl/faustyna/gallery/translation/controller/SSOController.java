package pl.faustyna.gallery.translation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.faustyna.gallery.translation.security.SecurityService;
import pl.faustyna.gallery.translation.security.SecurityToken;

@RestController
public class SSOController {

    @Autowired
    SecurityService securityService;

    @GetMapping("/login/{token}")
    public SecurityToken getSecurityToken(@PathVariable final String token) {

        return securityService.getSecurityTokenForSSOToken(token);
    }
}
