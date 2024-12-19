package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Chambre;
import com.example.demo.repository.ChambreRepository;

import java.util.List;

@Service
public class ChambreService {

    @Autowired
    private ChambreRepository chambreRepository;

    public List<Chambre> getAvailableChambres() {
        return chambreRepository.findByReserved("no");
    }
}
