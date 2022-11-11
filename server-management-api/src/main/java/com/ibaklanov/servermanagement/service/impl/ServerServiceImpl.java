package com.ibaklanov.servermanagement.service.impl;

import com.ibaklanov.servermanagement.model.Server;
import com.ibaklanov.servermanagement.repository.ServerRepository;
import com.ibaklanov.servermanagement.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Random;

import static com.ibaklanov.servermanagement.model.Status.SERVER_DOWN;
import static com.ibaklanov.servermanagement.model.Status.SERVER_UP;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {
    private final ServerRepository repository;

    @Override
    public Server create(Server server) {
        log.info("Saving new server {}", server.getName());
        server.setImageUrl(getServerImageUrl());
        return repository.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP {}", ipAddress);
        Server server = repository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        repository.save(server);
        return server;
    }

    @Override
    public List<Server> list(int limit) {
        log.info("Fetching all servers");
        return repository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by ID {}", id);
        return repository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server {}", server.getName());
        return repository.save(server);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting server by ID {}", id);
        repository.deleteById(id);
        return true;
    }

    private String getServerImageUrl() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/servers/image/" + imageNames[new Random().nextInt(imageNames.length)])
                .toUriString();
    }
}
