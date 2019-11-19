package com.example.cliente.service;

import com.example.cliente.entity.Cliente;

import java.util.List;

public interface IPersonaService {

    void  saveCliente (Cliente cliente);
    List<Cliente> findClientes ();
    void updateCliente (Cliente cliente, String id);

}
