package com.project.ecomproj;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface deporepo extends JpaRepository<productItem, Integer> {

}
