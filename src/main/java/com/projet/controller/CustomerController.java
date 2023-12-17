package com.projet.controller;

import com.projet.entities.Client;
import com.projet.metier.CustomerMetierInterface;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@RestController

@RequestMapping("/clients")
@CrossOrigin(origins = "http://localhost:4200/")
public class CustomerController {
    @Autowired





    CustomerMetierInterface customerMetier;

    @Autowired
    public CustomerController(CustomerMetierInterface customerMetier) {
        this.customerMetier = customerMetier;
    }
    @GetMapping(value ="/index" )
    public String accueil() {
        return "BienVenue au service Web REST 'produits'.....";
    }

    @PostMapping(
// spécifier le path de la méthode
            value = "/add" ,produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }
    )
    @CrossOrigin(origins = "http://localhost:4200")
    public Client saveProduit(@RequestBody Client p)
    {
        // Check if the image data is present

        return customerMetier.save(p);
    }

    @GetMapping(value= "/")
    @CrossOrigin(origins = "http://localhost:4200/")
    public List<Client> getAllProduits() {

        return customerMetier.listeClients();
    }
    @GetMapping(value= "/Actif", produces = "application/json")
    public List<Client> getActifCustomers() {

        return customerMetier.getActifCustomers();
    }
    @GetMapping(value = "/NonActif", produces = "application/json")
    public  List<Client> getNonActif(){
        return customerMetier.getNonActifCustomers();
    }


    @PutMapping(value = "/update/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public Client updateCustomer(@PathVariable Long customerId, @RequestBody Client clt) {

        return customerMetier.updateClient(customerId,clt);
    }








    @DeleteMapping(value = "/delete/{customerId}", produces = "application/json")
    public void deleteProduit(@PathVariable Long customerId)
    {
        customerMetier.deleteCustomer(customerId);
    }

    @GetMapping(value ="{customerId}" , produces = "application/json")
    public Optional<Client> betCltId (@PathVariable Long customerId){
        return customerMetier.findCltId(customerId);
    }

    @GetMapping(value = "/search", produces = "application/json")
    public List<Client> searchCustomers(@RequestParam(name = "keyword") String keyword) {

        return customerMetier.searchCustomers("%" + keyword + "%");
    }

    @GetMapping(value = "/searchadd", produces = "application/json")
    public List<Client> searchCustomersByAdd(@RequestParam(name = "adresse") String adresse) {

        return customerMetier.searchCustomerByadd("%" + adresse + "%");
    }

    @GetMapping(value = "/searchtel", produces = "application/json")
    public List<Client> searchCustomersByNumTel(@RequestParam(name = "NumTel") String NumTel) {

        return customerMetier.searchCustomerByNumTel("%" + NumTel + "%");
    }
    @GetMapping(value = "/{clientId}/getTotalRevenue", produces = "application/json")
    public Double getTotalRevenueForClient(@PathVariable Long clientId) {
        Optional<Client> client = customerMetier.findCltId(clientId);
        if (client.isPresent())
            return customerMetier.getTotalRevenueForClient(client.get());

        return 0.0;
    }


    @GetMapping(value = "/getTotalRevenue", produces = "application/json")
    public List<Object[]> getTotalRevenueForAllClient() {
        return customerMetier.getTotalRevenueForAllClients();


    }

    @GetMapping(value = "/getRemainingAmount", produces = "application/json")
    public List<Object[]> getRemainingAmountAllClient() {
        return customerMetier.getRemainingAmountForAllClients();
    }

    @GetMapping(value = "/{clientId}/getRemainingAmount", produces = "application/json")
    public Double getRemainingAmountForClient(@PathVariable Long clientId) {
        Optional<Client> client = customerMetier.findCltId(clientId);
        if (client.isPresent())
            return customerMetier.getRemainingAmountForClient(client.get());

        return 0.0;
    }

    @GetMapping("/paymentsClients")
    public List<Object[]> getPaymentsStatusForAllClients() {
        return customerMetier.getPaymentsStatusForAllClients();
    }
    @GetMapping("/{clientId}/hasUnpaidInvoices")
    public boolean hasUnpaidInvoices(@PathVariable Long clientId) {
        return customerMetier.hasUnpaidInvoices(clientId);
    }

    @GetMapping("/{clientId}/revenue/{year}")
    public List<Object[]> getRevenueByClientAndYear(@PathVariable Long clientId, @PathVariable String year) {
        // Retrieve the client by ID
        Client client = customerMetier.findCltId(clientId).get();

        return customerMetier.getRevenueByClientAndYear(client, year);
    }
    @GetMapping("/revenue/{year}")
    public List<Object[]> getRevenueByYear(@PathVariable String year) {
        return customerMetier.getRevenueByYear(year);
    }
    @GetMapping("/imprimer")
    public ResponseEntity<byte[]> downloadListCustomersVal() throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile("classpath:listeClients.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        List<Client> liste = customerMetier.listeClients();

        System.out.println(liste.size());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(liste);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + "downloadListCustomersList" + ".pdf\"")
                .body(JasperExportManager.exportReportToPdf(jasperPrint));
    }


    @GetMapping("/{clientId}/imprimer")
    public ResponseEntity<byte[]> downloadDetailClients(@PathVariable Long clientId) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile("classpath:detailClient.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // Retrieve the client data
        Optional<Client> optionalClient = customerMetier.findCltId(clientId);
        Client client = optionalClient.orElseThrow(() -> new RuntimeException("Client not found"));

        // Calculate remaining amount and total revenue
        Double remainingAmount = customerMetier.getRemainingAmountForClient(client);
        Double totalRevenue = customerMetier.getTotalRevenueForClient(client);

        // Prepare data source for the report
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(client));

        // Set parameters for the report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", clientId);
        parameters.put("totR", String.format("%.2f", totalRevenue));
        parameters.put("Ra", String.format("%.2f", remainingAmount));


        // Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export the report to PDF
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + "downloadDetailCustomersVal" + ".pdf\"")
                .body(pdfBytes);
    }


}
