package ru.ithub.nero.repository;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ithub.nero.model.entity.User;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    @Query(value = "Select count(u) > 0 from User u where u.name like %:keyword")
    boolean existsByName(@Param("keyword") String keyword);
}
