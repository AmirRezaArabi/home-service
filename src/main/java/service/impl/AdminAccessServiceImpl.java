package service.impl;

import domain.Expert;
import domain.Service;
import domain.SubService;
import domain.enums.EXPERT_STATUS;
import exception.InvalidServiceNameExctetion;
import exception.TheInputInformationIsNotValidException;
import lombok.RequiredArgsConstructor;
import service.*;


@RequiredArgsConstructor
public class AdminAccessServiceImpl implements AdminAccessService {


    private final ExpertService expertService;
    private  final CustomerService customerService;
    private  final ServiceService serviceService;
    private  final SubServiceService subServiceService;



    @Override
    public boolean addService(String serviceName) {
        Service service = Service.builder().name(serviceName).build();
        return serviceService.saveOrUpdate(service) != null;

    }

    @Override
    public boolean addSubService(String serviceName, String subServiceName, String description, Long basePrice) {
        if (serviceService.findByName(serviceName).isEmpty())
            throw new InvalidServiceNameExctetion("the input service name is not present");
        Service service = serviceService.findByName(serviceName).get();
        SubService SubService = domain.SubService.builder().service(service).name(subServiceName)
                .description(description).basePrice(basePrice).build();
        return subServiceService.saveOrUpdate(SubService) != null;
    }

    @Override
    public boolean updatePriceOrDescription(String SubServiceName, String description, Long basePrice) {
        if (subServiceService.findByName(SubServiceName).isEmpty())
            throw new InvalidServiceNameExctetion(" the input Sub service name is not valid");
        SubService SubService = subServiceService.findByName(SubServiceName).get();
        SubService.setBasePrice(basePrice);
        SubService.setDescription(description);
        return subServiceService.saveOrUpdate(SubService) != null;
    }

    @Override
    public boolean addExpertToSystem(String expertUserName, String subServiceName) {
        if (expertService.findByUserName(expertUserName).isEmpty() || subServiceService.findByName(subServiceName).isEmpty())
            throw new TheInputInformationIsNotValidException("the input information is not correct ");
        Expert expert = expertService.findByUserName(expertUserName).get();
        SubService subService = subServiceService.findByName(subServiceName).get();
        expert.getSubServices().add(subService);
        subService.getExpert().add(expert);
        return (subServiceService.saveOrUpdate(subService) != null && expertService.saveOrUpdate(expert) != null);

    }

    @Override
    public boolean removeExpertToSystem(String expertUserName, String subServiceName) {
        if (expertService.findByUserName(expertUserName).isEmpty() || subServiceService.findByName(subServiceName).isEmpty())
            throw new TheInputInformationIsNotValidException("the input information is not correct ");
        Expert expert = expertService.findByUserName(expertUserName).get();
        SubService subService = subServiceService.findByName(subServiceName).get();
        expert.getSubServices().remove(subService);
        subService.getExpert().remove(expert);
        return (subServiceService.saveOrUpdate(subService) != null && expertService.saveOrUpdate(expert) != null);
    }

    @Override
    public boolean confirmExpert(String expertUserName) {
        if (expertService.findByUserName(expertUserName).isEmpty())
            throw new TheInputInformationIsNotValidException("the input information is not correct ");
        Expert expert = expertService.findByUserName(expertUserName).get();
        expert.setExpert_status(EXPERT_STATUS.CONFIRMED);
        return ( expertService.saveOrUpdate(expert) != null);

    }
}
