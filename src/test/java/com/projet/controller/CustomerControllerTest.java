package com.projet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.entities.Client;
import com.projet.entities.Facture;
import com.projet.metier.CustomerMetierInterface;
import net.sf.jasperreports.engine.*;
// Assurez-vous d'avoir cette importation correcte
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerMetierInterface customerMetier;

    @InjectMocks
    private CustomerController customerController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testSaveProduit() throws Exception {
        Client client = new Client(); // create a Client object with necessary data
        given(customerMetier.save(ArgumentMatchers.any())).willReturn(client);

        mockMvc.perform(MockMvcRequestBuilders.post("/clients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(client)))
                .andExpect(status().isOk());

    }
    /*
    @Test
    public void testGetAllProduits() throws Exception {
        Client client1 = Client.builder().idCl(1L).nomCl("maissa").build();
        Client client2 = Client.builder().idCl(2L).nomCl("morched").build();
        List<Client> clients = Arrays.asList(client1, client2);

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.listeClients()).willReturn(clients);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant
        mockMvc.perform(get("/clients/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.size()").value(clients.size()))
                .andExpect(jsonPath("$[0].idCl").value(client1.getIdCl()))
                .andExpect(jsonPath("$[0].nomCl").value(client1.getNomCl()))
                .andExpect(jsonPath("$[1].idCl").value(client2.getIdCl()))
                .andExpect(jsonPath("$[1].nomCl").value(client2.getNomCl()));

        // Test pour une liste vide (ajoutez un cas où votre service renvoie une liste vide)
        given(customerMetier.listeClients()).willReturn(Collections.emptyList());
        mockMvc.perform(get("/clients/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.size()").value(0));
    }

*/
    @Test
    void testGetActifCustomers() throws Exception {
        // Créez quelques clients fictifs actifs pour le test
        Client client1 = Client.builder().idCl(1L).nomCl("maissa").actif(true).build();
        Client client2 = Client.builder().idCl(2L).nomCl("morched").actif(true).build();
        List<Client> actifClients = Arrays.asList(client1, client2);

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.getActifCustomers()).willReturn(actifClients);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant
        mockMvc.perform(get("/clients/Actif"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(actifClients.size()))
                .andExpect(jsonPath("$[0].idCl").value(client1.getIdCl()))
                .andExpect(jsonPath("$[0].nomCl").value(client1.getNomCl()))
                .andExpect(jsonPath("$[1].idCl").value(client2.getIdCl()))
                .andExpect(jsonPath("$[1].nomCl").value(client2.getNomCl()));
    }


    @Test
    void testGetNonActifCustomers() throws Exception {
        // Créez quelques clients fictifs non actifs pour le test
        Client client1 = Client.builder().idCl(1L).nomCl("maissa").actif(false).build();
        Client client2 = Client.builder().idCl(2L).nomCl("morched").actif(false).build();
        List<Client> nonActifClients = Arrays.asList(client1, client2);

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.getNonActifCustomers()).willReturn(nonActifClients);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant
        mockMvc.perform(get("/clients/NonActif"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(nonActifClients.size()))
                .andExpect(jsonPath("$[0].idCl").value(client1.getIdCl()))
                .andExpect(jsonPath("$[0].nomCl").value(client1.getNomCl()))
                .andExpect(jsonPath("$[1].idCl").value(client2.getIdCl()))
                .andExpect(jsonPath("$[1].nomCl").value(client2.getNomCl()));
    }

/*
    @Test
    public void testUpdateCustomer() throws Exception {
        // Given
        Long customerId = 1L;
        Client clientToUpdate = new Client(); // Replace Client with the actual type of your client

        // Mocking the behavior of customerMetier
        when(customerMetier.updateClient(any(Long.class), any(Client.class))).thenReturn(clientToUpdate);

        // When
        ResultActions result = mockMvc.perform(put("/update/{customerId}", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"property1\":\"value1\", \"property2\":\"value2\"}")); // Replace with actual JSON content

        // Then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.property1").value("value1")) // Replace with expected property and value
                .andExpect(jsonPath("$.property2").value("value2"));

        // Add more assertions based on your actual response structure

        // You can also verify that the customerMetier method was called with the expected arguments
        verify(customerMetier).updateClient(customerId, clientToUpdate);
    }*/
    @Test
    void testDeleteCustomer() throws Exception {
        // Configurez le comportement simulé de votre service métier
        doNothing().when(customerMetier).deleteCustomer(1L);

        ResultActions response = mockMvc.perform(delete("/clients/delete/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetCustomerById() throws Exception {
        // Créez un client fictif pour le test
        Client client = Client.builder().idCl(1L).nomCl("maissa").build();

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.findCltId(ArgumentMatchers.anyLong())).willReturn(Optional.of(client));

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant
        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.idCl").value(client.getIdCl()))
                .andExpect(jsonPath("$.nomCl").value(client.getNomCl()));
    }

    @Test
    void testSearchCustomers() throws Exception {
        // Créez quelques clients fictifs pour le test
        Client client1 = Client.builder().idCl(1L).nomCl("maissa").build();
        Client client2 = Client.builder().idCl(2L).nomCl("morched").build();
        List<Client> searchResults = Arrays.asList(client1, client2);

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.searchCustomers(ArgumentMatchers.anyString())).willReturn(searchResults);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant avec le paramètre de requête
        mockMvc.perform(get("/clients/search").param("keyword", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(searchResults.size()))
                .andExpect(jsonPath("$[0].idCl").value(client1.getIdCl()))
                .andExpect(jsonPath("$[0].nomCl").value(client1.getNomCl()))
                .andExpect(jsonPath("$[1].idCl").value(client2.getIdCl()))
                .andExpect(jsonPath("$[1].nomCl").value(client2.getNomCl()));
    }

    @Test
    void testSearchCustomersByAdd() throws Exception {
        // Créez quelques clients fictifs pour le test
        Client client1 = Client.builder().idCl(1L).nomCl("maissa").adresseCl("Paris").build();
        Client client2 = Client.builder().idCl(2L).nomCl("morched").adresseCl("London").build();
        List<Client> searchResults = Arrays.asList(client1, client2);

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.searchCustomerByadd(ArgumentMatchers.anyString())).willReturn(searchResults);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant avec le paramètre de requête
        mockMvc.perform(get("/clients/searchadd").param("adresse", "Paris"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(searchResults.size()))
                .andExpect(jsonPath("$[0].idCl").value(client1.getIdCl()))
                .andExpect(jsonPath("$[0].nomCl").value(client1.getNomCl()))
                .andExpect(jsonPath("$[0].adresseCl").value(client1.getAdresseCl()));
    }



    @Test
    void testSearchCustomersByNumTel() throws Exception {
        // Créez quelques clients fictifs pour le test
        Client client1 = Client.builder().idCl(1L).nomCl("maissa").numeroTelCl("29093786").build();
        Client client2 = Client.builder().idCl(2L).nomCl("morched").numeroTelCl("5555").build();
        List<Client> searchResults = Arrays.asList(client1, client2);

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.searchCustomerByNumTel(ArgumentMatchers.anyString())).willReturn(searchResults);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant avec le paramètre de requête
        mockMvc.perform(get("/clients/searchtel").param("NumTel", "123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(searchResults.size()))
                .andExpect(jsonPath("$[0].idCl").value(client1.getIdCl()))
                .andExpect(jsonPath("$[0].nomCl").value(client1.getNomCl()))
                .andExpect(jsonPath("$[0].numeroTelCl").value(client1.getNumeroTelCl()))
                .andExpect(jsonPath("$[1].idCl").value(client2.getIdCl()))
                .andExpect(jsonPath("$[1].nomCl").value(client2.getNomCl()))
                .andExpect(jsonPath("$[1].numeroTelCl").value(client2.getNumeroTelCl()));    }
    @Test
    void testGetTotalRevenueForClient() throws Exception {
        // Créez un client fictif pour le test
        Client client = Client.builder().idCl(1L).nomCl("maissa").build();

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.findCltId(ArgumentMatchers.anyLong())).willReturn(Optional.of(client));
        given(customerMetier.getTotalRevenueForClient(client)).willReturn(1000.0);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant avec le paramètre de chemin
        mockMvc.perform(get("/clients/{clientId}/getTotalRevenue", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").value(1000.0));
    }


    @Test
    void testGetTotalRevenueForAllClient() throws Exception {
        // Créez quelques données fictives pour le test
        Object[] result1 = new Object[] {1L, 1000.0};
        Object[] result2 = new Object[] {2L, 1500.0};
        List<Object[]> totalRevenueResults = Arrays.asList(result1, result2);

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.getTotalRevenueForAllClients()).willReturn(totalRevenueResults);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant
        mockMvc.perform(get("/clients/getTotalRevenue")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(totalRevenueResults.size()))
                .andExpect(jsonPath("$[0][0]").value(result1[0]))
                .andExpect(jsonPath("$[0][1]").value(result1[1]))
                .andExpect(jsonPath("$[1][0]").value(result2[0]))
                .andExpect(jsonPath("$[1][1]").value(result2[1]));
    }


    @Test
    void testGetRemainingAmountAllClient() throws Exception {
        // Créez quelques données fictives pour le test
        Object[] result1 = new Object[] {1L, 500.0};
        Object[] result2 = new Object[] {2L, 1000.0};
        List<Object[]> remainingAmountResults = Arrays.asList(result1, result2);

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.getRemainingAmountForAllClients()).willReturn(remainingAmountResults);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant
        mockMvc.perform(get("/clients/getRemainingAmount")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(remainingAmountResults.size()))
                .andExpect(jsonPath("$[0][0]").value(result1[0]))
                .andExpect(jsonPath("$[0][1]").value(result1[1]))
                .andExpect(jsonPath("$[1][0]").value(result2[0]))
                .andExpect(jsonPath("$[1][1]").value(result2[1]));
    }

    @Test
    void testGetRemainingAmountForClient() throws Exception {
        // ID du client pour le test
        Long clientId = 1L;

        // Créez quelques données fictives pour le test
        Double remainingAmount = 500.0;

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.findCltId(clientId)).willReturn(Optional.of(new Client()));
        given(customerMetier.getRemainingAmountForClient(any(Client.class))).willReturn(remainingAmount);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant
        mockMvc.perform(get("/clients/{clientId}/getRemainingAmount", clientId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").value(remainingAmount));
    }

/*
    @Test
    public void testGetPaymentsStatusForAllClients() throws Exception {
        // Mocking the customerMetier.getPaymentsStatusForAllClients() method
        List<Object[]> paymentsStatus = new ArrayList<>();
        // Add test data to paymentsStatus list

        when(customerMetier.getPaymentsStatusForAllClients()).thenReturn(paymentsStatus);

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/paymentsClients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());

        verify(customerMetier, times(1)).getPaymentsStatusForAllClients();
    }


    @Test
    public void testGetRevenueByClientAndYear() throws Exception {
        // Mocking the customerMetier.findCltId() method
        Long clientId = 1L;
        String year = "2023";
        Client mockClient = new Client();
        // Set mockClient properties

        when(customerMetier.findCltId(clientId)).thenReturn(Optional.of(mockClient));

        // Mocking the customerMetier.getRevenueByClientAndYear() method
        List<Object[]> revenueByClientAndYear = new ArrayList<>();
        // Add test data to revenueByClientAndYear list

        when(customerMetier.getRevenueByClientAndYear(mockClient, year)).thenReturn(revenueByClientAndYear);

        mockMvc.perform(MockMvcRequestBuilders.get("/{clientId}/revenue/{year}", clientId, year))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());

        verify(customerMetier, times(1)).findCltId(clientId);
        verify(customerMetier, times(1)).getRevenueByClientAndYear(mockClient, year);
    }


    @Test
    void testGetRevenueByYear() throws Exception {
        // Créez quelques données fictives pour le test
        Facture facture1 = new Facture();
        facture1.setIdFact(1L); // Assurez-vous d'ajuster en fonction de votre modèle de données
        Facture facture2 = new Facture();
        facture2.setIdFact(2L); // Assurez-vous d'ajuster en fonction de votre modèle de données
        Object[] revenueResult1 = new Object[]{facture1.getIdFact(), 500.0};
        Object[] revenueResult2 = new Object[]{facture2.getIdFact(), 700.0};
        List<Object[]> revenueResults = Arrays.asList(revenueResult1, revenueResult2);

        // Configurez le comportement simulé de votre service métier
        given(customerMetier.getRevenueByYear("2023")).willReturn(revenueResults);

        // Initialisez MockMvc en utilisant le contrôleur que vous testez
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Effectuez la requête GET sur le point de terminaison correspondant
        mockMvc.perform(get("/clients/revenue/2023")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(revenueResults.size()))
                .andExpect(jsonPath("$[0].idFact").value(facture1.getIdFact()))
                .andExpect(jsonPath("$[0].totalRevenue").value(revenueResult1[1]))
                .andExpect(jsonPath("$[1].idFact").value(facture2.getIdFact()))
                .andExpect(jsonPath("$[1].totalRevenue").value(revenueResult2[1]));
    }


    @Test
    public void testDownloadListCustomersVal() throws Exception {
        // Mocking the customerMetier.listeClients() method
        List<Client> clientList = new ArrayList<>();
        // Add test data to clientList

        when(customerMetier.listeClients()).thenReturn(clientList);

        mockMvc.perform(MockMvcRequestBuilders.get("/imprimer"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/pdf; charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Disposition", "inline; filename=\"" + "downloadListCustomersList" + ".pdf\""))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_PDF));

        verify(customerMetier, times(1)).listeClients();
    }


*/
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode utilitaire pour convertir un objet en format JSON
    private String asJsonObjet(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
