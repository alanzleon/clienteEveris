package com.example.cliente.repository;

import com.example.cliente.entity.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
@Repository
public interface IClienteRepository extends MongoRepository<Cliente, Serializable> {

}
