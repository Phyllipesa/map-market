package com.MapMarket.infrastructure.adapters.output.persistence;

import com.MapMarket.domain.models.User;
import com.MapMarket.domain.ports.output.FindByUserNameOutputPort;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.UserEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.logging.Logger;

public class UserPersistenceAdapter implements FindByUserNameOutputPort<User> {

  private final Logger logger = Logger.getLogger(UserPersistenceAdapter.class.getName());
  private final UserRepository userRepository;
  private final EntityMapper entityMapper;

  public UserPersistenceAdapter(UserRepository userRepository, EntityMapper entityMapper) {
    this.userRepository = userRepository;
    this.entityMapper = entityMapper;
  }

  @Override
  public Optional<User> findByUsername(String userName) throws UsernameNotFoundException {
    logger.info("Finding a user by username! PERSISTENCE");

    UserEntity userEntity = userRepository.findByUsername(userName);
    if (userEntity == null) {
      return Optional.empty();
    }

    return Optional.ofNullable(entityMapper.parseObject(userEntity, User.class));
  }
}
