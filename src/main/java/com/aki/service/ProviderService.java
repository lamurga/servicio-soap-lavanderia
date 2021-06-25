package com.aki.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aki.entity.Provider;
import com.aki.repository.ProviderRepository;

@Service
public class ProviderService implements IProviderService {
	@Autowired
	private ProviderRepository providerRepository;

	@Override
	public Provider getProviderById(long providerId) {
		Provider obj = providerRepository.findByProviderId(providerId);
		return obj;
	}	
	@Override
	public List<Provider> getAllProviders(){
		List<Provider> list = new ArrayList<>();
		providerRepository.findAll().forEach(e -> list.add(e));
		return list;
	}
	@Override
	public synchronized boolean addProvider(Provider provider){
	        List<Provider> list = providerRepository.findByTitleAndCategory(provider.getTitle(), provider.getCategory()); 	
                if (list.size() > 0) {
    	           return false;
                } else {
    	           provider = providerRepository.save(provider);
    	           return true;
                }
	}
	@Override
	public void updateProvider(Provider provider) {
		providerRepository.save(provider);
	}
	@Override
	public void deleteProvider(Provider provider) {
		providerRepository.delete(provider);
	}
} 