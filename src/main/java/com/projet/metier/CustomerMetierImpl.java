package com.projet.metier;

import com.projet.entities.Client;
import com.projet.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerMetierImpl implements  CustomerMetierInterface{
    @Autowired
    CustomerRepository customerRepository;



    @Override
    public void ajouterClient(Client clt) {


        customerRepository.save(clt);
    }

    @Override
    public List<Client> getActifCustomers() {
        return customerRepository.getActifCustomers();
    }

    @Override
    public List<Client> getNonActifCustomers() {
        return customerRepository.getNonActifCustomers();
    }

    @Override
    public List<Client> listeClients() {

        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(Long cltId) {

        customerRepository.deleteById(cltId);
    }

    @Override
    public Client save(Client p) {
        Client savedCustomer = customerRepository.save(p);
        return savedCustomer;
    }


    @Override
    public Optional<Client> findCltId(Long id) {
        return customerRepository.findById(id);
    }


    @Override
    public Client updateClient(Long customerId, Client updatedClient) {
        try {
            // Vérifier si le client existe dans la base de données
            Optional<Client> existingClientOptional = customerRepository.findById(customerId);

            if (existingClientOptional.isPresent()) {
                // Si le client existe, récupérer le client
                Client existingClient = existingClientOptional.get();

                // Mettre à jour les propriétés du client existant avec celles du nouveau client
                existingClient.setNomCl(updatedClient.getNomCl());
                existingClient.setPrenomCl(updatedClient.getPrenomCl());
                existingClient.setAdresseCl(updatedClient.getAdresseCl());
                existingClient.setEmailCl(updatedClient.getEmailCl());
                existingClient.setNumeroTelCl(updatedClient.getNumeroTelCl());
                existingClient.setActif(updatedClient.getActif());
                // Ajouter d'autres propriétés à mettre à jour

                // Enregistrer les modifications dans la base de données
                return customerRepository.save(existingClient);
            } else {
                // Gérer le cas où le client n'existe pas
                // Vous pouvez lancer une exception, retourner null ou effectuer d'autres actions en conséquence
                throw new EntityNotFoundException("Le client avec l'ID " + customerId + " n'a pas été trouvé.");
            }
        } catch (IllegalArgumentException e) {
            // Gérer l'exception IllegalArgumentException
            throw new IllegalArgumentException("Erreur lors de la mise à jour du client.", e);
        }
    }

    @Override
    public List<Client> searchCustomers(String keyword) {
        List<Client> customers=customerRepository.searchCustomer(keyword);
        return customers;
    }


    @Override
    public List<Client> searchCustomerByadd(String keyword) {
        return customerRepository.searchCustomerByadd( keyword);
    }

    @Override
    public List<Client> searchCustomerByNumTel(String keyword) {
        return customerRepository.searchCustomerByNumTel(keyword);
    }
    @Override
    public Double getTotalRevenueForClient(Client client) {
        return customerRepository.getTotalRevenueForClient(client);
    }

    @Override
    public List<Object[]> getTotalRevenueForAllClients() {
        return customerRepository.getTotalRevenueForAllClients();
    }

    @Override
    public List<Object[]> getRemainingAmountForAllClients() {
        return customerRepository.getRemainingAmountForAllClients();
    }

    @Override
    public Double getRemainingAmountForClient(Client client) {
        return customerRepository.getRemainingAmountForClient(client);
    }

    @Override
    public List<Object[]> getPaymentsStatusForAllClients() {
        return customerRepository.getPaymentsStatusForAllClients();
    }

    @Override
    public List<Object[]> getRevenueByClientAndYear( Client client, String year){
        return customerRepository.getRevenueByClientAndYear(client,year);
    }

    @Override
    public List<Object[]> getRevenueByYear(String year) {
        return customerRepository.getRevenueByYear(year);
    }

    @Override
    public boolean hasUnpaidInvoices(Long clientId) {
        return customerRepository.hasUnpaidInvoices(clientId);
    }
}
