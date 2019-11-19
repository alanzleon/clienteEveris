package com.example.cliente.service;

import com.example.cliente.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IPersonaService {

    public void  saveCliente (Cliente cliente);
    public List<Cliente> findClientes ();
    public Optional<Cliente> findClienteById(String id);

}
