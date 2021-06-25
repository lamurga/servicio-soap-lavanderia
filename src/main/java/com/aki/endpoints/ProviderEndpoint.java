package com.aki.endpoints;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.aki.entity.Provider;

import com.aki.gs_ws.AddProviderRequest;
import com.aki.gs_ws.AddProviderResponse;
import com.aki.gs_ws.ProviderInfo;
import com.aki.gs_ws.DeleteProviderRequest;
import com.aki.gs_ws.DeleteProviderResponse;
import com.aki.gs_ws.GetAllProvidersResponse;
import com.aki.gs_ws.GetProviderByIdRequest;
import com.aki.gs_ws.GetProviderByIdResponse;
import com.aki.gs_ws.ServiceStatus;
import com.aki.gs_ws.UpdateProviderRequest;
import com.aki.gs_ws.UpdateProviderResponse;
import com.aki.service.IProviderService;

@Endpoint
public class ProviderEndpoint {
	private static final String NAMESPACE_URI = "http://localhost:8080/provider-ws";
	
	@Autowired
	private IProviderService ProviderService;	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProviderByIdRequest")
	@ResponsePayload
	public GetProviderByIdResponse getProvider(@RequestPayload GetProviderByIdRequest request) {
		GetProviderByIdResponse response = new GetProviderByIdResponse();
		ProviderInfo ProviderInfo = new ProviderInfo();
		BeanUtils.copyProperties(ProviderService.getProviderById(request.getProviderId()), ProviderInfo);
		response.setProviderInfo(ProviderInfo);
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProvidersRequest")
	@ResponsePayload
	public GetAllProvidersResponse getAllProviders() {
		GetAllProvidersResponse response = new GetAllProvidersResponse();
		List<ProviderInfo> ProviderInfoList = new ArrayList<>();
		List<Provider> ProviderList = ProviderService.getAllProviders();
		for (int i = 0; i < ProviderList.size(); i++) {
		     ProviderInfo ob = new ProviderInfo();
		     BeanUtils.copyProperties(ProviderList.get(i), ob);
		     ProviderInfoList.add(ob);    
		}
		response.getProviderInfo().addAll(ProviderInfoList);
		return response;
	}	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addProviderRequest")
	@ResponsePayload
	public AddProviderResponse addProvider(@RequestPayload AddProviderRequest request) {
		AddProviderResponse response = new AddProviderResponse();		
    	        ServiceStatus serviceStatus = new ServiceStatus();		
		Provider Provider = new Provider();
		Provider.setTitle(request.getTitle());
		Provider.setCategory(request.getCategory());		
                boolean flag = ProviderService.addProvider(Provider);
                if (flag == false) {
        	   serviceStatus.setStatusCode("CONFLICT");
        	   serviceStatus.setMessage("Content Already Available");
        	   response.setServiceStatus(serviceStatus);
                } else {
		   ProviderInfo ProviderInfo = new ProviderInfo();
	           BeanUtils.copyProperties(Provider, ProviderInfo);
		   response.setProviderInfo(ProviderInfo);
        	   serviceStatus.setStatusCode("SUCCESS");
        	   serviceStatus.setMessage("Content Added Successfully");
        	   response.setServiceStatus(serviceStatus);
                }
                return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateProviderRequest")
	@ResponsePayload
	public UpdateProviderResponse updateProvider(@RequestPayload UpdateProviderRequest request) {
		Provider Provider = new Provider();
		BeanUtils.copyProperties(request.getProviderInfo(), Provider);
		ProviderService.updateProvider(Provider);
    	        ServiceStatus serviceStatus = new ServiceStatus();
    	        serviceStatus.setStatusCode("SUCCESS");
    	        serviceStatus.setMessage("Content Updated Successfully");
    	        UpdateProviderResponse response = new UpdateProviderResponse();
    	        response.setServiceStatus(serviceStatus);
    	        return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteProviderRequest")
	@ResponsePayload
	public DeleteProviderResponse deleteProvider(@RequestPayload DeleteProviderRequest request) {
		Provider Provider = ProviderService.getProviderById(request.getProviderId());
    	        ServiceStatus serviceStatus = new ServiceStatus();
		if (Provider == null ) {
	    	    serviceStatus.setStatusCode("FAIL");
	    	    serviceStatus.setMessage("Content Not Available");
		} else {
		    ProviderService.deleteProvider(Provider);
	    	    serviceStatus.setStatusCode("SUCCESS");
	    	    serviceStatus.setMessage("Content Deleted Successfully");
		}
    	        DeleteProviderResponse response = new DeleteProviderResponse();
    	        response.setServiceStatus(serviceStatus);
		return response;
	}	
}
