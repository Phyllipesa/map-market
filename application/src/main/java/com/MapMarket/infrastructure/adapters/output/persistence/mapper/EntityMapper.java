package com.MapMarket.infrastructure.adapters.output.persistence.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityMapper {

  private final ModelMapper modelMapper;

  @Autowired
  public EntityMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }


  //O codigo a seguir pode enviar uma InternalAuthenticationServiceException caso um dos GENERICOS seja null
  public <Origin, Destiny> Destiny parseObject(Origin origin, Class<Destiny> destination) {
    Objects.requireNonNull(origin, "Origin object in EntityMapper must not be null");
    Objects.requireNonNull(destination, "Destination type in EntityMapper must not be null");
    return modelMapper.map(origin, destination);
  }

  public <Origin, Destiny> List<Destiny> parseListObject(List<Origin> originList, Class<Destiny> destinationList) {
    Objects.requireNonNull(originList, "Origin list must not be null");
    Objects.requireNonNull(destinationList, "Destination type must not be null");
    List<Destiny> destinationObjects = new ArrayList<Destiny>();

    for (Origin origin : originList) {
      destinationObjects.add(parseObject(origin, destinationList));
    }
    return destinationObjects;
  }
}
