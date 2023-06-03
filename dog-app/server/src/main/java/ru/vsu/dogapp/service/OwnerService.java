package ru.vsu.dogapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.vsu.dogapp.dto.OwnerDto;
import ru.vsu.dogapp.entity.Owner;
import ru.vsu.dogapp.entity.type.Role;
import ru.vsu.dogapp.mapper.OwnerMapper;
import ru.vsu.dogapp.repository.DogRepository;
import ru.vsu.dogapp.repository.EventRepository;
import ru.vsu.dogapp.repository.OwnerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        user.setShow(true);
        repository.save(user);
        return true;
    }

    public void update(String username, OwnerDto ownerDto) {
        Owner ownerFromDB = repository.findByUsername(ownerDto.getUsername());
        if (ownerFromDB != null & !Objects.equals(username, ownerDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Login already taken");
        }
        Owner oldOwner = repository.findByUsername(username);
        Owner newOwner = mapper.toEntity(ownerDto);
        newOwner.setId(oldOwner.getId());
        newOwner.setPassword(bCryptPasswordEncoder.encode(newOwner.getPassword()));
        newOwner.setRoles(oldOwner.getRoles());
        repository.save(newOwner);
    }

    public void updatePassword(String username, String newPassword) {
        Owner owner = repository.findByUsername(username);
        owner.setPassword(bCryptPasswordEncoder.encode(newPassword));
        repository.save(owner);
    }

    public void delete(String username) {
        dogRepository.deleteAll(dogRepository.findAllByOwner_Username(username));
        eventRepository.deleteAll(eventRepository.findAllByOwner_Username(username));
        repository.delete(repository.findByUsername(username));
    }

    public OwnerDto find(String username) {
        return mapper.toDto(repository.findByUsername(username));
    }

    public void makeShowTrue() {
        List<Owner> owners = repository.findAll();
        for (Owner o: owners) {
            repository.save(new Owner(o.getId(), o.getUsername(), o.getPassword(), o.getName(), true, o.getRoles()));
        }
    }

    public void makeShowFalse(String username) {
        Owner owner = repository.findByUsername(username);
        owner.setShow(false);
        repository.save(owner);
    }
}
