package com.app.volunteer.dto.soap;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "userName",
        "password"
})
@XmlRootElement(name = "SoapUserLogin")
public class SoapUserLogin {
    private String userName;
    private String password;
}
