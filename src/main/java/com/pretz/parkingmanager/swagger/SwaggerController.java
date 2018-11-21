package com.pretz.parkingmanager.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

    private static final String SWAGGER_URL = "redirect:/swagger-ui.html";

    @GetMapping("${parking-manager.swagger.path}")
    public String swaggerMapping() {
        return SWAGGER_URL;
    }
}

