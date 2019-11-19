package com.example.cliente.repository;

import ch.qos.logback.core.net.server.Client;
import com.example.cliente.entity.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
@Repository
public interface IClienteRepository extends MongoRepository<Cliente, Serializable> {
    public Client findClienteById ();
}
