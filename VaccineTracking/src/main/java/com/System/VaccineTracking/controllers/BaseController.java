package com.System.VaccineTracking.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BaseController {
    @Qualifier("modelMapper")
    @Autowired
    protected ModelMapper modelMapper;
}
