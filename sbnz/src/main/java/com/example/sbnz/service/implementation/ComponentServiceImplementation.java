package com.example.sbnz.service.implementation;

import com.example.sbnz.model.Component;
import com.example.sbnz.repository.ComponentRepository;
import com.example.sbnz.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ComponentServiceImplementation implements ComponentService {

    @Autowired
    ComponentRepository componentRepository;

    @Override
    public Component create(Component component) {
        return componentRepository.save(component);
    }

    @Override
    public Component delete(Long id) {
        Component component = componentRepository.getOne(id);
        componentRepository.deleteComponentById(id);
        return component;
    }

    @Override
    public Collection<Component> getAll() {
        return componentRepository.findAll();
    }

    @Override
    public Component findById(Long id) {
        return componentRepository.getOne(id);
    }
}
