package com.projet.services;

import com.projet.entities.Client;
import com.projet.metier.CustomerMetierImpl;
import com.projet.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerMetierImpl customerMetier;




    @Test
    public void saveCustomerWithRepositoryException() {
        // Arrange
        Client clientToSave = createTestClient();

        // Configure the behavior of the repository's save method to throw an exception
        when(customerRepository.save(any(Client.class))).thenThrow(RuntimeException.class);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            customerMetier.save(clientToSave);
        });

        // Verify that the save method was called with the expected argument
        verify(customerRepository, times(1)).save(eq(clientToSave));
    }


    @Test
    public void testFindCltId() {
        // Créez un objet fictif pour simuler le résultat de la recherche dans le repository
        Client mockClient = new Client();
        mockClient.setIdCl(1L);
        mockClient.setNomCl("TestClient");

        // Configurez le comportement du repository mock lorsque la méthode findById est appelée
        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockClient));

        // Appelez la méthode du service qui utilise le repository
        Optional<Client> result = customerMetier.findCltId(1L);

        // Vérifiez que la méthode du repository a été appelée avec les bons arguments
        verify(customerRepository, times(1)).findById(1L);

        // Vérifiez que le résultat correspond à ce que vous attendez
        assertTrue(result.isPresent());
        assertEquals("TestClient", result.get().getNomCl());
    }

    @Test
    public void testFindCltIdWithNullId() {
        // Appelez la méthode du service avec un ID null
        Optional<Client> result = customerMetier.findCltId(null);

        // Vérifiez que le résultat est vide
        assertFalse(result.isPresent());

        // Vérifiez que la méthode du repository n'a pas été appelée
        verify(customerRepository, never()).findById(anyLong());
    }


    @Test
    public void findAll() {
        // Arrange
        List<Client> clients = Arrays.asList(createTestClient(), createTestClient());
        when(customerRepository.findAll()).thenReturn(clients);

        // Act
        List<Client> retrievedClients = customerMetier.listeClients();

        // Assert
        assertThat(retrievedClients).isNotNull();
        assertThat(retrievedClients.size()).isEqualTo(2);
    }

    @Test
    public void deleteCustomer() {
        // Arrange
        Long cltIdToDelete = 1L;

        // Act
        customerMetier.deleteCustomer(cltIdToDelete);

        // Verify that the deleteById method was called with the expected argument
        verify(customerRepository, times(1)).deleteById(eq(cltIdToDelete));
    }

    @Test
    public void updateCustomer() {
        // Arrange
        Long customerId = 1L;
        Client clientToUpdate = createTestClient();

        // Configure the behavior of the repository's save method
        when(customerRepository.save(any(Client.class))).thenReturn(clientToUpdate);
        // Assuming that the findById method is properly configured in customerRepository
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(clientToUpdate));

        // Act
        Client updatedClient = customerMetier.updateClient(customerId, clientToUpdate);

        // Assert
        assertThat(updatedClient).isNotNull();
        assertEquals(clientToUpdate, updatedClient);

        // Verify that the save method was called with the expected argument
        verify(customerRepository, times(1)).save(eq(clientToUpdate));
    }

    @Test
    public void searchCustomers() {
        // Arrange
        String keyword = "m";
        List<Client> expectedCustomers = Arrays.asList(createTestClient(), createTestClient());

        // Configure the behavior of the repository's searchCustomer method
        when(customerRepository.searchCustomer(keyword)).thenReturn(expectedCustomers);

        // Act
        List<Client> foundCustomers = customerMetier.searchCustomers(keyword);

        // Assert
        assertThat(foundCustomers).isNotNull();
        assertThat(foundCustomers).hasSize(2);

        // Verify that the searchCustomer method was called with the expected argument
        verify(customerRepository, times(1)).searchCustomer(eq(keyword));
    }

    @Test
    public void searchCustomersByAdd() {
        // Arrange
        String add = "sfaxn";
        List<Client> expectedCustomers = Arrays.asList(createTestClient(), createTestClient());

        // Configure the behavior of the repository's searchCustomerByadd method
        when(customerRepository.searchCustomerByadd(add)).thenReturn(expectedCustomers);

        // Act
        List<Client> foundCustomers = customerMetier.searchCustomerByadd(add);

        // Assert
        assertThat(foundCustomers).isNotNull();
        assertThat(foundCustomers).hasSize(2);

        // Verify that the searchCustomerByadd method was called with the expected argument
        verify(customerRepository, times(1)).searchCustomerByadd(eq(add));
    }


    @Test
    public void searchCustomersByNumTlfn() {
        // Arrange
        String num = "29";
        List<Client> expectedCustomers = Arrays.asList(createTestClient(), createTestClient());

        // Configure the behavior of the repository's searchCustomerByadd method
        when(customerRepository.searchCustomerByNumTel(num)).thenReturn(expectedCustomers);

        // Act
        List<Client> foundCustomers = customerMetier.searchCustomerByNumTel(num);

        // Assert
        assertThat(foundCustomers).isNotNull();
        assertThat(foundCustomers).hasSize(2);

        // Verify that the searchCustomerByadd method was called with the expected argument
        verify(customerRepository, times(1)).searchCustomerByNumTel(eq(num));
    }

    @Test
    public void testGetTotalRevenueForClient() {
        // Arrange
        Client client = createTestClient();
        Double expectedRevenue = 100.0;

        // Configure the behavior of the repository's getTotalRevenueForClient method
        when(customerRepository.getTotalRevenueForClient(client)).thenReturn(expectedRevenue);

        // Act
        Double actualRevenue = customerMetier.getTotalRevenueForClient(client);

        // Assert
        assertThat(actualRevenue).isEqualTo(expectedRevenue);

        // Verify that the getTotalRevenueForClient method was called with the expected argument
        verify(customerRepository, times(1)).getTotalRevenueForClient(eq(client));
    }

    @Test
    public void testGetTotalRevenueForAllClients() {
        // Arrange
        List<Object[]> expectedRevenueList = Arrays.asList(
                new Object[]{createTestClient(), 1000.0},
                new Object[]{createTestClient(), 2000.0}
        );

        // Configure the behavior of the repository's getTotalRevenueForAllClients method
        when(customerRepository.getTotalRevenueForAllClients()).thenReturn(expectedRevenueList);

        // Act
        List<Object[]> actualRevenueList = customerMetier.getTotalRevenueForAllClients();

        // Assert
        assertThat(actualRevenueList).isNotNull();
        assertThat(actualRevenueList).hasSize(2);

        // Verify that the getTotalRevenueForAllClients method was called
        verify(customerRepository, times(1)).getTotalRevenueForAllClients();
    }
    @Test
    public void testGetRemainingAmountForAllClients() {
        // Arrange
        List<Object[]> expectedRemainingAmountList = Arrays.asList(
                new Object[]{createTestClient(), 500.0},
                new Object[]{createTestClient(), 1000.0}
        );

        // Configure the behavior of the repository's getRemainingAmountForAllClients method
        when(customerRepository.getRemainingAmountForAllClients()).thenReturn(expectedRemainingAmountList);

        // Act
        List<Object[]> actualRemainingAmountList = customerMetier.getRemainingAmountForAllClients();

        // Assert
        assertThat(actualRemainingAmountList).isNotNull();
        assertThat(actualRemainingAmountList).hasSize(2);

        // Verify that the getRemainingAmountForAllClients method was called
        verify(customerRepository, times(1)).getRemainingAmountForAllClients();
    }

    @Test
    public void testGetRemainingAmountForClient() {
        // Arrange
        Client client = createTestClient();
        Double expectedRemainingAmount = 500.0;

        // Configure the behavior of the repository's getRemainingAmountForClient method
        when(customerRepository.getRemainingAmountForClient(client)).thenReturn(expectedRemainingAmount);

        // Act
        Double actualRemainingAmount = customerMetier.getRemainingAmountForClient(client);

        // Assert
        assertThat(actualRemainingAmount).isEqualTo(expectedRemainingAmount);

        // Verify that the getRemainingAmountForClient method was called with the expected argument
        verify(customerRepository, times(1)).getRemainingAmountForClient(eq(client));
    }

    @Test
    public void testGetPaymentsStatusForAllClients() {
        // Arrange
        List<Object[]> expectedPaymentsList = Arrays.asList(
                new Object[]{createTestClient(), "Paid"},
                new Object[]{createTestClient(), "Pending"}
        );

        // Configure the behavior of the repository's getPaymentsForAllClients method
        when(customerRepository.getPaymentsStatusForAllClients()).thenReturn(expectedPaymentsList);

        // Act
        List<Object[]> actualPaymentsList = customerMetier.getPaymentsStatusForAllClients();

        // Assert
        assertThat(actualPaymentsList).isNotNull();
        assertThat(actualPaymentsList).hasSize(2);

        // Verify that the getPaymentsForAllClients method was called
        verify(customerRepository, times(1)).getPaymentsStatusForAllClients();
    }

    @Test
    public void testGetRevenueByClientAndYear() {
        // Arrange
        Client client = createTestClient();
        String year = "2023";
        List<Object[]> expectedRevenueList = Arrays.asList(
                new Object[]{createTestClient(), 1000.0},
                new Object[]{createTestClient(), 2000.0}
        );

        // Configure the behavior of the repository's getRevenueByClientAndYear method
        when(customerRepository.getRevenueByClientAndYear(client, year)).thenReturn(expectedRevenueList);

        // Act
        List<Object[]> actualRevenueList = customerMetier.getRevenueByClientAndYear(client, year);

        // Assert
        assertThat(actualRevenueList).isNotNull();
        assertThat(actualRevenueList).hasSize(2);

        // Verify that the getRevenueByClientAndYear method was called with the expected arguments
        verify(customerRepository, times(1)).getRevenueByClientAndYear(eq(client), eq(year));
    }

    @Test
    public void testGetRevenueByYear() {
        // Arrange
        String year = "2023";
        List<Object[]> expectedRevenueList = Arrays.asList(
                new Object[]{createTestClient(), 1000.0},
                new Object[]{createTestClient(), 2000.0}
        );

        // Configure the behavior of the repository's getRevenueByYear method
        when(customerRepository.getRevenueByYear(year)).thenReturn(expectedRevenueList);

        // Act
        List<Object[]> actualRevenueList = customerMetier.getRevenueByYear(year);

        // Assert
        assertThat(actualRevenueList).isNotNull();
        assertThat(actualRevenueList).hasSize(2);

        // Verify that the getRevenueByYear method was called with the expected argument
        verify(customerRepository, times(1)).getRevenueByYear(eq(year));
    }

    private Client createTestClient() {
        return Client.builder()
                .idCl(1L)
                .codeCl("clt1")
                .nomCl("maissa")
                .prenomCl("ellouze")
                .adresseCl("sfax")
                .emailCl("maissaellouze02@gmail.com")
                .numeroTelCl("29 093 786")
                .actif(true)
                .build();
    }

}
