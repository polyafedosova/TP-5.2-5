package ru.vsu.dogapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.dogapp.dto.OwnerDto;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.entity.type.Role;
import ru.vsu.dogapp.mapper.OwnerMapper;
import ru.vsu.dogapp.repository.OwnerRepository;

import java.util.Collections;
import java.util.List;

@Service
public class OwnerService implements UserDetailsService {

    private final OwnerRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OwnerMapper mapper;

    public OwnerService(OwnerRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, OwnerMapper mapper) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner = repository.findByUsername(username);
        if (owner == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return owner;
    }

    public boolean save(OwnerDto owner) {
        Owner ownerFromDB = repository.findByUsername(owner.getUsername());
        if (ownerFromDB != null) return false;

        var user = mapper.toEntity(owner);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        repository.save(user);
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
