package com.example.demo.repository;

import com.example.demo.entity.Chambre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChambreRepository extends MongoRepository<Chambre, String> {
    List<Chambre> findByReserved(String reserved);
}
