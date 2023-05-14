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
import ru.vsu.dogapp.repository.DogRepository;
import ru.vsu.dogapp.repository.EventRepository;
import ru.vsu.dogapp.repository.OwnerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService implements UserDetailsService {

    private final OwnerRepository repository;
    private final DogRepository dogRepository;
    private final EventRepository eventRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OwnerMapper mapper;

    public OwnerService(OwnerRepository repository, DogRepository dogRepository, EventRepository eventRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder, OwnerMapper mapper) {
        this.repository = repository;
        this.dogRepository = dogRepository;
        this.eventRepository = eventRepository;
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
        Owner user = mapper.toEntity(owner);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        repository.save(user);
        return true;
    }

    public void update(Integer id, OwnerDto ownerDto) {
        Owner oldOwner = repository.findOwnerById(id);
        Owner newOwner = mapper.toEntity(ownerDto);
        newOwner.setId(oldOwner.getId());
        newOwner.setPassword(bCryptPasswordEncoder.encode(newOwner.getPassword()));
        newOwner.setRoles(oldOwner.getRoles());
        repository.save(newOwner);
    }

    public void updatePassword(Integer id, String oldPassword, String newPassword) {
        Owner owner = repository.findOwnerById(id);
        if (bCryptPasswordEncoder.encode(oldPassword).equals(owner.getPassword())) {
            owner.setPassword(bCryptPasswordEncoder.encode(newPassword));
        } else {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }

    public void delete(Integer id) {
        dogRepository.deleteAll(dogRepository.findAllByOwner_Id(id));
        eventRepository.deleteAll(eventRepository.findAllByOwner_Id(id));
        repository.delete(repository.findOwnerById(id));
    }

    public OwnerDto find(Integer id) {
        return mapper.toDto(repository.findOwnerById(id));
    }

    public OwnerDto find(String username) {
        return mapper.toDto(repository.findByUsername(username));
    }
}
