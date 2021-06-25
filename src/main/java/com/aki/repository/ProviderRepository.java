package com.aki.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.aki.entity.Provider;

public interface ProviderRepository extends CrudRepository<Provider, Long>  {
	Provider findByProviderId(long providerId);
        List<Provider> findByTitleAndCategory(String title, String category);
}