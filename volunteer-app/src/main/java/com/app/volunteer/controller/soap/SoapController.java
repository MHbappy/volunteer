package com.app.volunteer.controller.soap;

import com.app.volunteer.dto.soap.SoapToken;
import com.app.volunteer.dto.soap.SoapUserLogin;
import com.app.volunteer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapController {

    private static final String NAMESPACE = "http://www.volunteer.com/spring/soap/api/loadEligibility";

    @Autowired
    private UserService userService;

    @PayloadRoot(namespace =  NAMESPACE, localPart = "SoapUserLogin")
    @ResponsePayload
    public SoapToken soapToken(@RequestPayload SoapUserLogin soapUserLogin){
        String token = userService.signin(soapUserLogin.getUserName(), soapUserLogin.getPassword());
        return new SoapToken(token);
    }

}
