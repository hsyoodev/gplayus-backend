package com.gplayus.backend.repository;

import com.gplayus.backend.domain.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App, Long> {

}
