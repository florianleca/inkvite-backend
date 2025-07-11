package com.flolecinc.inkvitebackend.tattoos.clients;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TattooClientService {

    private TattooClientRepository tattooClientRepository;

    @Transactional
    public TattooClient saveClient(TattooClient client) {
        return tattooClientRepository.save(client);
    }

}
