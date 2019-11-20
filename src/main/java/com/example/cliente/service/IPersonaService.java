package com.example.cliente.service;

import com.example.cliente.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IPersonaService {

    String saveCliente (Cliente cliente);
    List<Cliente> findClientes ();
    String updateCliente (Cliente cliente, String id);
    Cliente findClienteById(String id);
}
