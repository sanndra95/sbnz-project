package com.example.sbnz.controller;

import com.example.sbnz.model.Component;
import com.example.sbnz.service.ComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api/component")
@CrossOrigin
public class ComponentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ComponentService componentService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Component component) {
        logger.info("> component: {}", component.getName());

        component.setDeleted(false);

        Component c = componentService.create(component);

        logger.info("> create component: {}", c.getName());
        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("> deleting component with id: {} ", id);

        Component component = componentService.delete(id);
        logger.info("> deleted: {}", component.getName());
        return new ResponseEntity<>(component, HttpStatus.OK);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getAll() {
        logger.info("> getting all components");
        Collection<Component> components = componentService.getAll();
        logger.info("size: {}", components.size());
        return new ResponseEntity<>(components, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getComponent(@PathVariable Long id) {
        Component component = componentService.findById(id);
        logger.info("getting component: {}", component.getName());
        return new ResponseEntity<>(component, HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Component component) {
        Component c = componentService.findById(component.getId());
        logger.info("> updating component: {}", c.getName());
        c.setName(component.getName());
        Component updated = componentService.create(c);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
