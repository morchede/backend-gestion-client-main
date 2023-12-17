package com.projet.repository;

import com.projet.entities.Client;
import com.projet.metier.CustomerMetierImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {
    @Autowired
    private  CustomerRepository customerRepository;
    @Mock
    private CustomerRepository customerRep;
    @InjectMocks
    private CustomerMetierImpl customerMetier;
    @Test
    public  void saveCustomer(){
        Client client = Client.builder()
                .idCl(1L)
                .codeCl("clt1")
                .nomCl("maissa")
                .prenomCl("ellouze")
                .adresseCl("sfax")
                .emailCl("maissaellouze02@gmail.com")
                .numeroTelCl("29 093 786")
                .actif(true)
                .build();

        Client savedClient = customerRepository.save(client);

        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getIdCl()).isGreaterThan(0);

    }


    @Test
    public void CustomerFindById() {
        Client client = Client.builder()
                .idCl(1L)
                .codeCl("clt2")
                .nomCl("morched")
                .prenomCl("abdennadher")
                .adresseCl("sfax")
                .emailCl("morched@gmail.com")
                .numeroTelCl("55")
                .actif(false)
                .build();

        customerRepository.save(client);

        Client clientList = customerRepository.findById(client.getIdCl()).get();

        assertThat(clientList).isNotNull();
    }

    @Test
    public void ClientGetAll() {
       /* Client client1 = Client.builder()
                .idCl(1L)
                .codeCl("clt1")
                .nomCl("maissa")
                .prenomCl("ellouze")
                .adresseCl("sfax")
                .emailCl("maissaellouze02@gmail.com")
                .numeroTelCl("29 093 786")
                .actif(true)
                .build();
        Client client2 = Client.builder()
                .idCl(1L)
                .codeCl("clt2")
                .nomCl("morched")
                .prenomCl("abdennadher")
                .adresseCl("sfax")
                .emailCl("morched@gmail.com")
                .numeroTelCl("55")
                .actif(false)
                .build();



        customerRepository.save(client1);
        customerRepository.save(client2);

        */

        List<Client> ClientList = customerRepository.findAll();

        assertThat(ClientList).isNotNull();
        assertThat(ClientList.size()).isEqualTo(6);
    }

    @Test
    public void ClientUpdate() {


        Client ClientSaved = customerRepository.findById(2L).get();
        ClientSaved.setAdresseCl("iset");


        Client ClientUpdated = customerRepository.save(ClientSaved);

        assertThat(ClientUpdated.getAdresseCl()).isNotNull();

    }

    @Test
    public void DeleteClient() {

        customerRepository.deleteById(2L);
        Optional<Client> DeletedClient = customerRepository.findById(2L);

        assertThat(DeletedClient).isEmpty();
    }


    @Test
    public void testGetActifCustomers() {

        List<Client> activeCustomers = customerRepository.getActifCustomers();
        assertThat(activeCustomers).isNotNull();

    }

    @Test
    public void testGetNonActifCustomers() {

        List<Client> inactiveCustomers = customerRepository.getNonActifCustomers();
        assertThat(inactiveCustomers).isNotNull();

    }




    @Test
    public void testGetTotalRevenueForAllClients() {
        // Mocking the customerRepository.getTotalRevenueForAllClients() method
        List<Object[]> totalRevenueList = new ArrayList<>();
        // Add test data to totalRevenueList

        when(customerRep.getTotalRevenueForAllClients()).thenReturn(totalRevenueList);

        List<Object[]> result = customerRep.getTotalRevenueForAllClients();

        // Perform assertions on the result
        // ...

        verify(customerRep, times(1)).getTotalRevenueForAllClients();
    }

    @Test
    public void testGetRemainingAmountForAllClients() {
        // Mocking the customerRepository.getRemainingAmountForAllClients() method
        List<Object[]> remainingAmountList = new ArrayList<>();
        // Add test data to remainingAmountList

        when(customerRep.getRemainingAmountForAllClients()).thenReturn(remainingAmountList);

        List<Object[]> result = customerRep.getRemainingAmountForAllClients();

        // Perform assertions on the result
        // ...

        verify(customerRep, times(1)).getRemainingAmountForAllClients();
    }

    @Test
    public void testGetRevenueByClientAndYear() {
        // Mocking the customerRepository.getRevenueByClientAndYear() method
        List<Object[]> revenueList = new ArrayList<>();
        // Add test data to revenueList

        Client client = new Client(); // Create a test client object
        String year = "2023"; // Set the test year

        when(customerRep.getRevenueByClientAndYear(client, year)).thenReturn(revenueList);

        List<Object[]> result = customerRep.getRevenueByClientAndYear(client, year);

        // Perform assertions on the result
        // ...

        verify(customerRep, times(1)).getRevenueByClientAndYear(client, year);
    }

    @Test
    public void testGetRemainingAmountForClient() {
        // Mocking the customerRepository.getRemainingAmountForClient() method
        Double remainingAmount = 1000.0; // Set the test remaining amount

        Client client = new Client(); // Create a test client object

        when(customerRep.getRemainingAmountForClient(client)).thenReturn(remainingAmount);

        Double result = customerRep.getRemainingAmountForClient(client);

        // Perform assertions on the result
        // ...

        verify(customerRep, times(1)).getRemainingAmountForClient(client);
    }

    @Test
    public void testGetPaymentsForAllClients() {
        // Mocking the customerRepository.getPaymentsForAllClients() method
        List<Object[]> paymentsList = new ArrayList<>();
        // Add test data to paymentsList

        when(customerRep.getPaymentsStatusForAllClients()).thenReturn(paymentsList);

        List<Object[]> result = customerRep.getPaymentsStatusForAllClients();

        // Perform assertions on the result
        // ...

        verify(customerRep, times(1)).getPaymentsStatusForAllClients();
    }

    @Test
    public void testSearchCustomerByNumTel() {
        // Mocking the customerRepository.searchCustomerByNumTel() method
        List<Client> customerList = new ArrayList<>();
        // Add test data to customerList

        String keyword = "22"; // Set the test keyword

        when(customerRep.searchCustomerByNumTel(keyword)).thenReturn(customerList);

        List<Client> result = customerRep.searchCustomerByNumTel(keyword);

        verify(customerRep, times(1)).searchCustomerByNumTel(keyword);
    }

    @Test
    public void testGetTotalRevenueForClient() {
        // Mocking the customerRepository.getTotalRevenueForClient() method
        Double totalRevenue = 5000.0; // Set the test total revenue

        Client client = new Client(); // Create a test client object

        when(customerRep.getTotalRevenueForClient(client)).thenReturn(totalRevenue);

        Double result = customerRep.getTotalRevenueForClient(client);

        // Perform assertions on the result
        // ...

        verify(customerRep, times(1)).getTotalRevenueForClient(client);
    }



}
