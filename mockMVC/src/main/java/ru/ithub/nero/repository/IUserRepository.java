package ru.ithub.nero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.ithub.nero.model.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
}
