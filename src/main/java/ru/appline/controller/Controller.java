package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;
import ru.appline.logic.UpdateReq;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "plain/text")
    public String createPet(@RequestBody Pet pet){

        petModel.add(pet, newId.getAndIncrement());
        if (petModel.getAll().size()==1){
            return "Поздравляем, вы создали первого питомца!";
        } else {
            return "Поздравляем, вы создали нового питомца!";
        }
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll(){return petModel.getAll();}

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String,Integer> id){
        return petModel.getFromList(id.get("id"));
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "plain/text")
    public String deletePet(@RequestBody Map<String, Integer> id){
        if (petModel.getAll().containsKey(id.get("id"))){
            petModel.getAll().remove(id.get("id"));
            return "Питомец с id " + id.get("id") + " удалён";
        } else {
            return "Питомца с id " + id.get("id") + " не существует";
        }
    }

    @PutMapping(value = "/updatePet", consumes = "application/json", produces = "plain/text")
    public String updatePet(@RequestBody UpdateReq updateReq){
        if(petModel.getAll().containsKey(updateReq.getId())){
            if(petModel.getFromList(updateReq.getId()).equals(updateReq.getPet())){
                return "Обновление не произведено, данные идентичны";
            } else {
                petModel.getAll().put(updateReq.getId(), updateReq.getPet());
                return "Данные обновлены";
            }
        } else {
            return "Питомца с id " + updateReq.getId() + " не существует";
        }
    }

}
