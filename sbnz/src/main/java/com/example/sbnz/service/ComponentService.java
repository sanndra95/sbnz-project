package com.example.sbnz.service;

import com.example.sbnz.model.Component;

import java.util.Collection;

public interface ComponentService {

    Component create(Component component);

    Component delete(Long id);

    Collection<Component> getAll();

    Component findById(Long id);
}
