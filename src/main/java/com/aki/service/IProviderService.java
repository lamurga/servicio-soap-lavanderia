package com.aki.service;

import java.util.List;
import com.aki.entity.Provider;

public interface IProviderService {
     List<Provider> getAllProviders();
     Provider getProviderById(long providerId);
     boolean addProvider(Provider provider);
     void updateProvider(Provider provider);
     void deleteProvider(Provider provider);
}
