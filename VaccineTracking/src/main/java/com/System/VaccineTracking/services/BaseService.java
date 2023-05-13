package com.System.VaccineTracking.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BaseService {
    @Qualifier("modelMapper")
    @Autowired
    protected ModelMapper modelMapper;
}
