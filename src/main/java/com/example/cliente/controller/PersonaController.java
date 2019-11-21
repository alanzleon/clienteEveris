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
            switch (respuestaService) {
                case "ok":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Cliente creado correctamente\"}", HttpStatus.CREATED);
                    break;
                case "NoRut":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Falta Rut\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "NoEdad":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Edad debe estar entre 25 y 100\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "NoNombre":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Falta Nombre\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "NoApellidoPaterno":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Falta Apellido Paterno\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "noApellidoMaterno":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Falta Apellido Materno\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "NoSexo":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Falta Sexo\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "InvalidSexo":
                    response = new ResponseEntity<>("{\"Mensaje\":\"El sexo debe ser Masculino o Femenino\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "NoDireccion":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Falta Direccion\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "NoTelefono":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Falta Telefono\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "NoTipoLicencia":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Falta Tipo de licencia\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "invalidTipoLicencia":
                    response = new ResponseEntity<>("{\"Mensaje\":\"El tipo de licencia debe ser 'A', 'B' o 'C'\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "noFechaEmision":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Falta fecha emision licencia\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "noFechaVencimiento":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Falta fecha de vencimiento licencia\"}", HttpStatus.BAD_REQUEST);
                    break;
                case "invalidRut":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Ingrese un rut valido\"}", HttpStatus.CREATED);
                    break;
                case "invalidTelefono":
                    response = new ResponseEntity<>("{\"Mensaje\":\"Ingrese un telefono valido\"}", HttpStatus.CREATED);
                    break;
                default:
                    response = new ResponseEntity<>("{\"Error\":\"Algo salio mal :c\"}",HttpStatus.INTERNAL_SERVER_ERROR);
                    break;
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
