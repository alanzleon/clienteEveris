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
public class ClienteController {

    @Autowired
    private PersonaService service;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        ResponseEntity<?> response;
        try {
            List<Cliente> clientes = this.service.findClientes();
            response = new ResponseEntity<>(clientes, HttpStatus.OK);
        } catch (Exception ex) {
            response = new ResponseEntity<>(mensajeError("Algo salio mal"), HttpStatus.INTERNAL_SERVER_ERROR);
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
            response = new ResponseEntity<>(mensajeError("Algo salio mal")+ ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;

    }

    @GetMapping("/{rut}")
    public ResponseEntity<?> getClienteByRut(@PathVariable(value = "rut") String rut){
        ResponseEntity<?> response;
        try{
            Cliente cliente = this.service.findClienteByRut(rut);
            response = new ResponseEntity<>(cliente, HttpStatus.OK);
        }catch (Exception ex) {
            System.out.println(ex);
            response = new ResponseEntity<>(mensajeError(ex.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping()
    public ResponseEntity<?> addCliente (@RequestBody Cliente cliente){
        ResponseEntity<?> response;
        String respuestaService = this.service.saveCliente(cliente);
        try{
            switch (respuestaService) {
                case "ok":
                    response = new ResponseEntity<>(mensaje("Cliente creado correctamente"), HttpStatus.CREATED);
                    break;
                case "NoRut":
                    response = new ResponseEntity<>(mensajeError("Falta Rut"), HttpStatus.BAD_REQUEST);
                    break;
                case "NoEdad":
                    response = new ResponseEntity<>(mensajeError("Edad debe estar entre 25 y 100"), HttpStatus.BAD_REQUEST);
                    break;
                case "NoNombre":
                    response = new ResponseEntity<>(mensajeError("Falta Nombre"), HttpStatus.BAD_REQUEST);
                    break;
                case "NoApellidoPaterno":
                    response = new ResponseEntity<>(mensajeError("Falta Apellido Paterno"), HttpStatus.BAD_REQUEST);
                    break;
                case "noApellidoMaterno":
                    response = new ResponseEntity<>(mensajeError("Falta Apellido Materno"), HttpStatus.BAD_REQUEST);
                    break;
                case "NoSexo":
                    response = new ResponseEntity<>(mensajeError("Falta Sexo"), HttpStatus.BAD_REQUEST);
                    break;
                case "InvalidSexo":
                    response = new ResponseEntity<>(mensajeError("El sexo debe ser Masculino o Femenino"), HttpStatus.BAD_REQUEST);
                    break;
                case "NoDireccion":
                    response = new ResponseEntity<>(mensajeError("Falta Direccion"), HttpStatus.BAD_REQUEST);
                    break;
                case "NoTelefono":
                    response = new ResponseEntity<>(mensajeError("Falta Telefono"), HttpStatus.BAD_REQUEST);
                    break;
                case "NoTipoLicencia":
                    response = new ResponseEntity<>(mensajeError("Falta Tipo de licencia"), HttpStatus.BAD_REQUEST);
                    break;
                case "invalidTipoLicencia":
                    response = new ResponseEntity<>(mensajeError("El tipo de licencia debe ser 'A', 'B' o 'C'"), HttpStatus.BAD_REQUEST);
                    break;
                case "noFechaEmision":
                    response = new ResponseEntity<>(mensajeError("Falta fecha emision licencia"), HttpStatus.BAD_REQUEST);
                    break;
                case "noFechaVencimiento":
                    response = new ResponseEntity<>(mensajeError("Falta fecha de vencimiento licencia"), HttpStatus.BAD_REQUEST);
                    break;
                case "invalidRut":
                    response = new ResponseEntity<>(mensajeError("Ingrese un rut valido"), HttpStatus.BAD_REQUEST);
                    break;
                case "invalidTelefono":
                    response = new ResponseEntity<>(mensajeError("Ingrese un telefono valido"), HttpStatus.BAD_REQUEST);
                    break;
                case "rutExistente":
                    response = new ResponseEntity<>(mensajeError("El cliente ya existe"), HttpStatus.BAD_REQUEST);
                    break;
                default:
                    response = new ResponseEntity<>(mensajeError("Algo salio mal"),HttpStatus.INTERNAL_SERVER_ERROR);
                    break;
            }
        } catch (Exception ex) {
            response = new ResponseEntity<>(mensajeError(ex.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //@RequestMapping(value = "/actualizar/{rut}", method = RequestMethod.PUT)
    @PutMapping("/{rut}")
    public ResponseEntity<?> actualizar(@RequestBody Cliente cliente, @PathVariable(value = "rut") String rut){
        ResponseEntity<?> response;
        String respuestaService = this.service.updateCliente(cliente,rut);
        try {
            switch(respuestaService) {
                case "update":
                    response = new ResponseEntity<>(mensaje("Cliente actualizado"), HttpStatus.OK);
                    break;
                case "notFound":
                    response = new ResponseEntity<>(mensajeError("Cliente No existe"),HttpStatus.NOT_FOUND);
                    break;
                case "invalidEdad":
                    response = new ResponseEntity<>(mensajeError("La edad debe estar entre 25 y 100 años"),HttpStatus.BAD_REQUEST);
                    break;
                case "invalidSexo":
                    response = new ResponseEntity<>(mensajeError("El sexo debe ser Masculino o Femenino"),HttpStatus.BAD_REQUEST);
                    break;
                case "invalidTelefono":
                    response = new ResponseEntity<>(mensajeError("Ingrese un telefono valido"),HttpStatus.BAD_REQUEST);
                    break;
                case "invalidTipoLicencia":
                    response = new ResponseEntity<>(mensajeError("El tipo de licencia debe ser 'A', 'B' o 'C'"),HttpStatus.BAD_REQUEST);
                    break;
                default:
                    response = new ResponseEntity<>(mensajeError("Algo salio mal"),HttpStatus.INTERNAL_SERVER_ERROR);
                    break;
            }
        } catch (Exception ex){
            response = new ResponseEntity<>(mensajeError(ex.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public String mensajeError(String msjPersonalizado) {
        return "{\"Error\":\""+msjPersonalizado+"\"}";
    }

    public String mensaje(String msjPersonalizado) {
        return "{\"Mensaje\":\""+msjPersonalizado+"\"}";
    }
}
