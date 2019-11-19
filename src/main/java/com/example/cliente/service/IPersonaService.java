package com.example.cliente.service;

import com.example.cliente.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IPersonaService {

    void  saveCliente (Cliente cliente);
    List<Cliente> findClientes ();
    void updateCliente (Cliente cliente, String id);
    Optional<Cliente> findClienteById(String id);
}
