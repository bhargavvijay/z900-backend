package com.z900.z900;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://z900-frontend.onrender.com") // allow frontend dev origin
@RequestMapping("/accessories")
public class Contoller {

    @Autowired
    private Repository repo;

    // GET all
    @GetMapping
    public List<Model> getAccessories() {
        return repo.findAll();
    }

    // CREATE
    @PostMapping
    public Model createAccessory(@RequestBody Model model) {
        model.setId(null); // let DB generate id
        return repo.save(model);
    }

    // UPDATE (expects full object with id)
    @PutMapping
    public Model updateAccessory(@RequestBody Model model) {
        Model existing = repo.findById(model.getId())
                .orElseThrow(() -> new RuntimeException("Accessory not found"));
        existing.setName(model.getName());
        existing.setLink(model.getLink());
        existing.setPrice(model.getPrice());
        return repo.save(existing); // persist changes
    }

    // DELETE by id
    @DeleteMapping("/{id}")
    public void deleteAccessory(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
