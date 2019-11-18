package com.example.cliente.service;

import com.example.cliente.entity.Cliente;
import com.example.cliente.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PersonaService implements IPersonaService{
    @Autowired
    private IClienteRepository repository;

    @Override
    public void saveCliente(Cliente cliente) {
        this.repository.save(cliente);
    }

    @Override
    public List<Cliente> findClientes() {
        return this.repository.findAll();
    }


}
