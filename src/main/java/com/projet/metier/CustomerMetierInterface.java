package com.projet.metier;

import com.projet.entities.Client;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerMetierInterface {
    void ajouterClient(Client clt);
    List<Client>  getActifCustomers();
    List<Client>  getNonActifCustomers();
    List<Client> listeClients();
    void deleteCustomer(Long cltId);

    Client save(Client p);
    Optional<Client> findCltId(Long id);
    Client updateClient(Long customerId, Client updatedClient);
    List<Client> searchCustomers(String keyword);
    List<Client> searchCustomerByadd(String keyword);
    List<Client> searchCustomerByNumTel(String keyword);
    Double getTotalRevenueForClient(Client client);
    List<Object[]> getTotalRevenueForAllClients();
    List<Object[]> getRemainingAmountForAllClients();
    Double getRemainingAmountForClient(Client client);
    List<Object[]> getPaymentsStatusForAllClients();
    List<Object[]> getRevenueByClientAndYear( Client client, String year);
    List<Object[]> getRevenueByYear(@Param("year") String year);
    boolean hasUnpaidInvoices(@Param("clientId") Long clientId);


}
