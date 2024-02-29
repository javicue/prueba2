package com.javiersan.prueba.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.javiersan.prueba.exceptions.NaveNotFoundException;
import com.javiersan.prueba.models.entity.Nave;
import com.javiersan.prueba.models.service.INaveService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController; 

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
@Api(value = "Controlador Naves", description = "Esta API tiene un CRUD para naves")
public class NavesRestController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired 
    private INaveService naveService;

    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    @ApiOperation(value = "Lista todas las naves", notes = "retorna un listado paginado de todas las naves")
	public String indice(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = pageRequest.of(page, 4); 
   
		
		Page<Nave> naves = naveService.findAll(pageRequest);

		PageRender<Nave> pageRender = new PageRender<Nave>("/listar", naves); 
		model.addAttribute("titulo", "Listado de naves");
		model.addAttribute("naves", naves);
		model.addAttribute("page", pageRender);
		return "listar";
	}

  
    @Secured({"ROLE_USER"})
    @GetMapping("/naves/{id}")
    @ApiOperation(value = "busca una nave por su id", notes = "retorna la nave con el id indicado")
    public ResponseEntity<?> mostrar(@PathVariable Long id) {


        Nave nave = naveService.findById(id).orElseThrow( () -> new NaveNotFoundException("Error, la nave no exite"));        
        
        return new ResponseEntity<Nave>(nave,HttpStatus.OK);
    }

    @GetMapping(value = "/nave/{term}", produces = { "application/json" }) 
    @ApiOperation(value = "Busca las naves que coincidan con el término dado", notes = "retorna en el body una lista de las naves que contienen ese término ")
	public @ResponseBody List<Nave> cargarNaves(@PathVariable String term) {

		return naveService.findByName(term);
	}


    @Secured("ROLE_ADMIN")
    @PostMapping("/naves")
    @ApiOperation(value = "Crea una nave nueba en la base de datos", notes = "retorna un objeto con la response y el httpStatus correspondiente")
    public ResponseEntity<?> crear(@Valid @PathVariable Nave nave, BindingResult result) {

        Nave naveNueva = null;
        Map<String, Object> respuesta = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
               .stream()
               .map(err -> {
                return "El campo '" + err.getField() +"' " + err.getDefaultMessage();
               })
               .collect(Collectors.toList());

               respuesta.put("errors", errors);
               return new ResponseEntity<Map<String,Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

        
        naveNueva = naveService.save(nave);

        respuesta.put("mensaje", "La nave  ha sido creado con éxito!");
        respuesta.put("nave", naveNueva);
        return new ResponseEntity<Map<String,Object>>(respuesta, HttpStatus.CREATED);


    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/naves/{id}")
    @ApiOperation(value = "Editar unna nave de la base de datos", notes = "retorna retorna un objeto con la response y el httpStatus correspondiente ")
    public ResponseEntity<?> editar(@Valid @RequestParam Nave nave, BindingResult result, @PathVariable Long id) {

        Nave naveActual = naveService.findById(id);

        Nave naveEditada = null;

        Map<String, Object> respuesta = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
            .stream()
            .map(err -> {
                return "El campo '" + err.getField() +"' " + err.getDefaultMessage();
            })
            .collect(Collectors.toList());

            respuesta.put("errors", errors);
            return new ResponseEntity<Map<String,Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

            naveActual.setNombre(nave.getNombre());
            naveActual.setModelo(nave.getModelo());
            naveActual.setCreateAt(nave.getCreateAt());

            naveEditada = naveService.save(naveActual);

        respuesta.put("mensaje", "La nave ha sido actualizada con éxito!");
        respuesta.put("nave", naveEditada);
        return new ResponseEntity<Map<String,Object>>(respuesta, HttpStatus.CREATED);

    }


    @Secured("ROLE_ADMIN")
    @DeleteMapping("/naves/{id}")
    @ApiOperation(value = "Borra la nave con id pasado por usuario de la base de datos", notes = "retorna una response y el httpStatus correspondiente")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        Map<String, Object> respuesta = new HashMap<>();

    
            naveService.delete(id);

            respuesta.put("mensaje", "La nave ha sido eliminada con éxito!");

        return new ResponseEntity<Map<String,Object>>(respuesta, HttpStatus.OK);
    }
    
    
    
}
