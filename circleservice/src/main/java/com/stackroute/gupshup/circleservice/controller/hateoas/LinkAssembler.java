package com.stackroute.gupshup.circleservice.controller.hateoas;

import java.util.List;

import com.stackroute.gupshup.circleservice.model.*;

public interface LinkAssembler
{
    Iterable<Circle> assembleLinksForCircleList(List<Circle> circle);
  
    
}
