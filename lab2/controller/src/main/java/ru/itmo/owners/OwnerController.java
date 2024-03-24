package ru.itmo.owners;
//реализацию уже с спрингом напишу, чтобы сразу все роуты прописать и тд, а не реактировать все это, методы предположительно такие будут
public interface OwnerController {
    void addNewOwner();
    void getOwnerById();
    void addCat();
    void deleteOwner();
    void getAllCats();
}
