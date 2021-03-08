package com.auth.authorization.controller;

import com.auth.authorization.handlers.AuthHandler;
import com.jumbotail.common.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private AuthHandler authHandler;

    @Autowired
    public AuthController(@Qualifier("authHandler") AuthHandler authHandler){
        this.authHandler= authHandler;
    }

    @RequestMapping("authorize")
    public RestResponse<Boolean> isUserAuthorized(@RequestParam("path") String path, @RequestParam("userId") String userId)
    {
        return RestResponse.createSuccessResponse(authHandler.isUserAuthorized(path, userId));
    }

}
