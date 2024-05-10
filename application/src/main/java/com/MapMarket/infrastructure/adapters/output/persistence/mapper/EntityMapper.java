package com.MapMarket.infrastructure.adapters.output.persistence.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EntityMapper {

  @Autowired
  private ModelMapper modelMapper;

  public <Origin, Destiny> Destiny parseObject(Origin origin, Class<Destiny> destination) {
    return modelMapper.map(origin, destination);
  }

  public <Origin, Destiny> List<Destiny> parseListObject(List<Origin> originList, Class<Destiny> destinationList) {
    List<Destiny> destinationObjects = new ArrayList<Destiny>();

    for (Origin origin : originList) {
      destinationObjects.add(parseObject(origin, destinationList));
    }
    return destinationObjects;
  }
}
