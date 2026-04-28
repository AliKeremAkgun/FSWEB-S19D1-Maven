package com.workintech.s18d2.controller;

import com.workintech.s18d2.entity.Fruit;
import com.workintech.s18d2.exceptions.PlantException;
import com.workintech.s18d2.services.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruit")
@Validated
public class FruitController {

    private final FruitService fruitService;

    @Autowired
    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    // [GET]/workintech/fruits (Testlerde /fruit) => Price ASC
    @GetMapping
    public List<Fruit> findAll() {
        return fruitService.getByPriceAsc();
    }

    // [GET]/workintech/fruits/desc (Testlerde /fruit/desc) => Price DESC
    // Bu metot /{id} metodunun üstünde olmalı ki 'desc' kelimesi id sanılmasın
    @GetMapping("/desc")
    public List<Fruit> findAllDesc() {
        return fruitService.getByPriceDesc();
    }

    // [GET]/workintech/fruits/{id}
    @GetMapping("/{id}")
    public Fruit find(@PathVariable("id") Long id) {
        if (id == null || id < 0) {
            throw new PlantException("Id zero'dan küçük olamaz: " + id, HttpStatus.BAD_REQUEST);
        }
        return fruitService.getById(id);
    }

    // [GET]/workintech/fruits/name/{name} (Testlerde /fruit/name/{name})
    @GetMapping("/name/{name}")
    public List<Fruit> findByName(@PathVariable("name") String name) {
        return fruitService.searchByName(name);
    }

    // [POST]/workintech/fruits => Save or Update
    @PostMapping
    public Fruit save(@RequestBody Fruit fruit) {
        return fruitService.save(fruit);
    }

    // [DELETE]/workintech/fruits/{id}
    @DeleteMapping("/{id}")
    public Fruit delete(@PathVariable("id") Long id) {
        if (id == null || id < 0) {
            throw new PlantException("Geçersiz Id", HttpStatus.BAD_REQUEST);
        }
        return fruitService.delete(id);
    }
}