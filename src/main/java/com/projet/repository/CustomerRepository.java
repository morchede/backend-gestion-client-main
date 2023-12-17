package com.projet.repository;

import com.projet.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Client,Long> {
    @Query("select c from Client c where c.actif=true ")
    List<Client> getActifCustomers();

    @Query("select c from Client c where c.actif=false ")
    List<Client> getNonActifCustomers();

    @Query("select c from Client c where c.nomCl like :kwd or c.prenomCl like :kwd")
    List<Client> searchCustomer(@Param("kwd") String keyword);

    @Query("select c from Client c where c.adresseCl like :kwd ")
    List<Client> searchCustomerByadd(@Param("kwd") String keyword);

    @Query("select c from Client c where c.numeroTelCl like :kwd ")
    List<Client> searchCustomerByNumTel(@Param("kwd") String keyword);

    // Query to get the total revenue for a specific client
    @Query("SELECT SUM(df.qte * df.prixunitaire) FROM Facture f " +
            "JOIN f.detailFactures df " +
            "WHERE f.client = :client AND f.idFact = df.facture.idFact")
    Double getTotalRevenueForClient(@Param("client") Client client);


    // Query to get the total revenue for all clients
    @Query("SELECT c, SUM(df.qte * df.prixunitaire) FROM Client c " +
            "JOIN c.factures f " +
            "JOIN f.detailFactures df " +
            "GROUP BY c")
    List<Object[]> getTotalRevenueForAllClients();

    // Le reste global des montants non encore payés for all clients
    @Query("SELECT c, SUM(df.qte * df.prixunitaire) AS totalAmount, " +
            "SUM(COALESCE(rf.montant, 0)) AS totalPayments " +
            "FROM Client c " +
            "JOIN c.factures f " +
            "JOIN f.detailFactures df " +
            "LEFT JOIN f.reglementFactures rf " +
            "GROUP BY c")
    List<Object[]> getRemainingAmountForAllClients();


    @Query("SELECT SUM(df.qte * df.prixunitaire) - COALESCE(SUM(rf.montant), 0) " +
            "FROM Client c " +
            "JOIN c.factures f " +
            "JOIN f.detailFactures df " +
            "LEFT JOIN f.reglementFactures rf " +
            "WHERE c = :client")
    Double getRemainingAmountForClient(@Param("client") Client client);


    @Query("SELECT f, " +
            "SUM(COALESCE(rf.montant, 0)) AS totalPayments " +
            "FROM Client c " +
            "JOIN c.factures f " +
            "LEFT JOIN f.reglementFactures rf " +
            "GROUP BY f, c")
    List<Object[]> getPaymentsStatusForAllClients();


    @Query("SELECT c, SUBSTRING(f.dateFact, 1, 4) as year, SUM(df.qte * df.prixunitaire) AS totalRevenue " +
            "FROM Client c " +
            "JOIN c.factures f " +
            "JOIN f.detailFactures df " +
            "WHERE c = :client AND SUBSTRING(f.dateFact, 1, 4) = :year " +
            "GROUP BY c, SUBSTRING(f.dateFact, 1, 4)")
    List<Object[]> getRevenueByClientAndYear(@Param("client") Client client, @Param("year") String year);


    @Query("SELECT c, SUBSTRING(f.dateFact, 1, 4) as year, SUM(df.qte * df.prixunitaire) AS totalRevenue " +
            "FROM Client c " +
            "JOIN c.factures f " +
            "JOIN f.detailFactures df " +
            "WHERE SUBSTRING(f.dateFact, 1, 4) = :year " +
            "GROUP BY c, SUBSTRING(f.dateFact, 1, 4)")
    List<Object[]> getRevenueByYear(@Param("year") String year);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM Facture f " +
            "WHERE f.client.idCl = :clientId "  // Assuming idClient is in the Client entity
    )
    boolean hasUnpaidInvoices(@Param("clientId") Long clientId);




}
