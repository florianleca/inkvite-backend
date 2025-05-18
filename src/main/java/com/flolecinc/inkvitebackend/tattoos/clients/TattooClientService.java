package com.flolecinc.inkvitebackend.tattoos.clients;

import com.flolecinc.inkvitebackend.tattoos.requestforms.RequestFormDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TattooClientService {

    private TattooClientRepository tattooClientRepository;

    @Transactional
    public TattooClientEntity saveClientFromIdentity(RequestFormDto.IdentityDto identity) {
        TattooClientEntity client = new TattooClientEntity();
        client.setFirstName(identity.getFirstName());
        client.setLastName(identity.getLastName());
        client.setEmail(identity.getEmail());
        return tattooClientRepository.save(client);
    }

}
