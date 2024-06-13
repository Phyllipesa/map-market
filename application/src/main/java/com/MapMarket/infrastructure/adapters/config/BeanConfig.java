package com.MapMarket.infrastructure.adapters.config;

import com.MapMarket.application.rest.AuthRestAdapter;
import com.MapMarket.application.rest.responseDto.LocationResponseDto;
import com.MapMarket.application.rest.responseDto.ProductResponseDto;
import com.MapMarket.domain.logic.CredentialsValidator;
import com.MapMarket.domain.logic.ProductValidator;
import com.MapMarket.domain.logic.RefreshCredentialsValidator;
import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.models.Product;
import com.MapMarket.domain.models.User;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.domain.ports.output.FindByUserNameOutputPort;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.domain.service.AuthService;
import com.MapMarket.domain.service.LocationService;
import com.MapMarket.domain.service.ProductService;
import com.MapMarket.domain.service.UserService;
import com.MapMarket.domain.service.security.jwt.JwtTokenFilter;
import com.MapMarket.domain.service.security.jwt.JwtTokenProvider;
import com.MapMarket.infrastructure.adapters.output.persistence.LocationPersistenceAdapter;
import com.MapMarket.infrastructure.adapters.output.persistence.ProductPersistenceAdapter;
import com.MapMarket.infrastructure.adapters.output.persistence.UserPersistenceAdapter;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.LocationEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.LocationRepository;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.ProductRepository;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class BeanConfig {

  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(ProductEntity.class, Product.class)
        .addMappings(mapper -> {
          mapper.map(ProductEntity::getId, Product::setId);
        });
    modelMapper.typeMap(Product.class, ProductResponseDto.class)
        .addMappings(mapper -> {
          mapper.map(Product::getId, ProductResponseDto::setKey);
        });

    modelMapper.typeMap(LocationEntity.class, Location.class)
        .addMappings(mapper -> {
          mapper.map(LocationEntity::getId, Location::setId);
        });
    modelMapper.typeMap(Location.class, LocationResponseDto.class)
        .addMappings(mapper -> {
          mapper.map(Location::getId, LocationResponseDto::setKey);
        });
    return modelMapper;
  }

  @Bean
  public EntityMapper entityMapper() {
    return new EntityMapper(modelMapper());
  }

  @Bean
  public ProductValidator validationProduct() {
    return new ProductValidator();
  }

  @Bean
  public CredentialsValidator validationCredentials() {
    return new CredentialsValidator();
  }

  @Bean
  public RefreshCredentialsValidator refreshCredentialsValidator() {
    return new RefreshCredentialsValidator();
  }

  @Bean
  public ProductPersistenceAdapter productPersistenceAdapter(ProductRepository productRepository, EntityMapper entityMapper) {
    return new ProductPersistenceAdapter(productRepository, entityMapper);
  }

  @Bean
  public ProductService productService(OutputPort<Product> outputPort, FindAllOutput<Product> findAllOutput, ProductValidator productValidator, PagedResourcesAssembler<ProductResponseDto> pagedResourcesAssembler, EntityMapper entityMapper) {
    return new ProductService(outputPort, findAllOutput, productValidator, pagedResourcesAssembler, entityMapper);
  }

  @Bean
  public UserPersistenceAdapter userPersistenceAdapter(UserRepository userRepository, EntityMapper entityMapper) {
    return new UserPersistenceAdapter(userRepository, entityMapper);
  }

  @Bean
  public UserService userService(FindByUserNameOutputPort<User> outputPort) {
    return new UserService(outputPort);
  }

  @Bean
  public JwtTokenProvider jwtTokenProvider(UserDetailsService userDetailsService) {
    return new JwtTokenProvider(userDetailsService);
  }

  @Bean
  public JwtTokenFilter jwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    return new JwtTokenFilter(jwtTokenProvider);
  }

  @Bean
  AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws  Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthService authService(FindByUserNameOutputPort<User> outputPort, AuthenticationManager authenticationManager, JwtTokenProvider  jwtTokenProvider) {
    return new AuthService(outputPort, authenticationManager, jwtTokenProvider);
  }

  @Bean
  public AuthRestAdapter authRestAdapter(AuthService authService, CredentialsValidator credentialsValidator, RefreshCredentialsValidator  refreshCredentialsValidator) {
    return new AuthRestAdapter(authService, credentialsValidator, refreshCredentialsValidator);
  }

  @Bean
  public LocationPersistenceAdapter locationPersistenceAdapter(LocationRepository locationRepository, EntityMapper entityMapper) {
    return new LocationPersistenceAdapter(locationRepository, entityMapper);
  }

  @Bean
  public LocationService locationService(OutputPort<Location> outputPort, FindAllOutput<Location> findAllOutput, PagedResourcesAssembler<LocationResponseDto> pagedResourcesAssembler, EntityMapper entityMapper) {
    return new LocationService(outputPort, findAllOutput, pagedResourcesAssembler, entityMapper);
  }
}
