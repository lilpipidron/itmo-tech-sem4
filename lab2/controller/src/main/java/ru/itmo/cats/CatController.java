package ru.itmo.cats;

//реализацию уже с спрингом напишу, чтобы сразу все роуты прописать и тд, а не реактировать все это, методы предположительно такие будут
public interface CatController {
    void addNewCat();
    void getCatById();
    void getAllFriends();
    void addFriend();
    void deleteCat();
}
