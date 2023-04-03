package ru.vsu.dogapp.service;

import org.springframework.stereotype.Service;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.repository.OwnerRepository;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository repository;

    public OwnerService(OwnerRepository repository) {
        this.repository = repository;
    }

    //    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public OwnerService(OwnerRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.repository = repository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
////    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Owner owner = repository.findByUsername(username);
//        if (owner == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return owner;
//    }

    public boolean save(Owner owner) {
        Owner ownerFromDB = repository.findByUsername(owner.getUsername());
        if (ownerFromDB != null) return false;
//        owner.setPassword(bCryptPasswordEncoder.encode(owner.getPassword()));
        repository.save(owner);
        return true;
    }

    public List<Owner> getAll() {
        return repository.findAll();
    }

    public void update(Integer id, Owner owner) {
        Owner oldOwner = repository.findOwnerById(id);
        owner.setId(oldOwner.getId());
        repository.save(owner);
    }

    public void delete(Integer id) {
        repository.delete(repository.findOwnerById(id));
    }
    public boolean delete(Owner owner) {
        if (repository.findById(owner.getId()).isPresent()) {
            repository.deleteById(owner.getId());
            return true;
        } return false;
    }

    public Owner find(Integer id) {
        return repository.findOwnerById(id);
    }
}
