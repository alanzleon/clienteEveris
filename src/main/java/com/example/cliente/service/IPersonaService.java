package com.example.cliente.service;

import com.example.cliente.entity.Cliente;

import java.util.List;

public interface IPersonaService {

    public void  saveCliente (Cliente cliente);
    public List<Cliente> findClientes ();

}
