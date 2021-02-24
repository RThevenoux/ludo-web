package com.example.tick;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TickRepository extends JpaRepository<TickEntity, Long> {

}
