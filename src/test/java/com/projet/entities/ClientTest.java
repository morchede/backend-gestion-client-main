package com.projet.entities;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class ClientTest {
    @Mock
    private Facture factureMock;

    @InjectMocks
    private Client client;

    @Test
    public void testClientBuilder() {
        // Créez un ensemble de factures pour le test
        Set<Facture> factures = new HashSet<>();
        factures.add(factureMock);

        // Utilisez le builder pour créer une instance de Client
        Client client = Client.builder()
                .idCl(1L)
                .codeCl("clt1")
                .nomCl("ellouze")
                .prenomCl("maissa")
                .adresseCl("sfax")
                .emailCl("maissaellouze")
                .numeroTelCl("29")
                .actif(true)
                .factures(factures)
                .build();

        // Configurez le comportement du mock
        when(factureMock.getIdFact()).thenReturn(1L);


        assertEquals("clt1", client.getCodeCl());
        assertEquals("ellouze", client.getNomCl());
        assertEquals("maissa", client.getPrenomCl());
        assertEquals("sfax", client.getAdresseCl());
        assertEquals("maissaellouze", client.getEmailCl());
        assertEquals("29", client.getNumeroTelCl());
        assertEquals(true, client.getActif());
        assertEquals(factures, client.getFactures());

        // Vérifiez le comportement du mock
        assertEquals(1L, client.getFactures().iterator().next().getIdFact().longValue());
    }
}
