package com.example.cliente.controller;

import com.example.cliente.entity.Cliente;
import com.example.cliente.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/cliente")
@CrossOrigin(value={})
public class PersonaController {

    @Autowired
    private PersonaService service;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        ResponseEntity<?> response;
        try {
            List<Cliente> clientes = this.service.findClientes();
            response = new ResponseEntity<>(clientes, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            response = new ResponseEntity<>("{\"Error\":\"Algo salio mal :c\"}"+ ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;

    }
    @GetMapping("/getById")
    public ResponseEntity<?> getClienteById(String id){
        ResponseEntity<?> response;
        try{
            Cliente cliente = this.service.findClienteById(id);
            response = new ResponseEntity<>(cliente, HttpStatus.OK);
        }catch (Exception ex) {
            System.out.println(ex);
            response = new ResponseEntity<>("{\"Error\":\"Algo salio mal :c\"}"+ ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;

    }

    @PostMapping("/addCliente")
    public ResponseEntity<?> addCliente (@RequestBody Cliente cliente){
        ResponseEntity<?> response;
        String respuestaService = this.service.saveCliente(cliente);
        try{
            if(respuestaService.equals("ok")) {
                response = new ResponseEntity<>("{\"Mensaje\":\"Cliente creado correctamente\"}", HttpStatus.CREATED);
            } else {
                response = new ResponseEntity<>("{\"Error\":\"Edad debe ser mayor a 25 años\"}",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            response = new ResponseEntity<>("{\"Error\":\"Algo salio mal :c\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(value = "/actualizar/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> actualizar(@RequestBody Cliente cliente, @PathVariable(value = "id") String id){
        ResponseEntity<?> response;
        String respuestaService = this.service.updateCliente(cliente,id);
        try {
            if(respuestaService.equals("update")){
                response = new ResponseEntity<>(cliente, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>("{\"Error\":\"Usuario No existe\"}",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex){
            response = new ResponseEntity<>("{\"Error\":\"Algo salio mal :c\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
