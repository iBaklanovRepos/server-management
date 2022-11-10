package com.ibaklanov.servermanagement.service;

import com.ibaklanov.servermanagement.model.Server;

import java.io.IOException;
import java.util.List;

public interface ServerService {
    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    List<Server> list(int limit);
    Server get(Long id);
    Server update(Server server);
    boolean delete(Long id);
}
