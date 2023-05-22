package ru.appline.logic;

public class UpdateReq {
    private int id;
    private Pet pet;

    public UpdateReq(){
        super();
    }

    public UpdateReq(int id, Pet pet) {
        this.id = id;
        this.pet = pet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
