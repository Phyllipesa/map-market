package com.MapMarket.infrastructure.adapters.config;

import com.MapMarket.application.rest.AuthRestAdapter;
import com.MapMarket.application.rest.responseDto.ProdutoResponseDto;
import com.MapMarket.domain.logic.CredentialsValidator;
import com.MapMarket.domain.logic.ProductValidator;
import com.MapMarket.domain.logic.RefreshCredentialsValidator;
import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.models.User;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.domain.ports.output.FindByUserNameOutputPort;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.domain.service.AuthService;
import com.MapMarket.domain.service.ProdutoService;
import com.MapMarket.domain.service.UserService;
import com.MapMarket.domain.service.security.jwt.JwtTokenFilter;
import com.MapMarket.domain.service.security.jwt.JwtTokenProvider;
import com.MapMarket.infrastructure.adapters.output.persistence.ProdutoPersistenceAdapter;
import com.MapMarket.infrastructure.adapters.output.persistence.UserPersistenceAdapter;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProdutoEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.ProdutoRepository;
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
    modelMapper.typeMap(ProdutoEntity.class, Produto.class)
        .addMappings(mapper -> {
          mapper.map(ProdutoEntity::getId, Produto::setId);
        });
    modelMapper.typeMap(Produto.class, ProdutoResponseDto.class)
        .addMappings(mapper -> {
          mapper.map(Produto::getId, ProdutoResponseDto::setKey);
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
  public ProdutoPersistenceAdapter produtoPersistenceAdapter(ProdutoRepository produtoRepository, EntityMapper entityMapper) {
    return new ProdutoPersistenceAdapter(produtoRepository, entityMapper);
  }

  @Bean
  public ProdutoService produtoService(
      OutputPort<Produto> outputPort,
      FindAllOutput<Produto> findAllOutput,
      ProductValidator productValidator,
      PagedResourcesAssembler<ProdutoResponseDto> pagedResourcesAssembler,
      EntityMapper entityMapper
  ) {
    return new ProdutoService(outputPort, findAllOutput, productValidator, pagedResourcesAssembler, entityMapper);
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
}
