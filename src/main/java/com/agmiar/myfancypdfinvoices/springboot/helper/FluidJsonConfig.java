package com.agmiar.myfancypdfinvoices.springboot.helper;

import tools.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FluidJsonConfig {

    @Autowired
    public void configurarFluidJson(ObjectMapper mapper) {
        FluidJson.setMapper(mapper);
    }
}
