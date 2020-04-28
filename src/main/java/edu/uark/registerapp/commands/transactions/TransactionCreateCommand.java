package edu.uark.registerapp.commands.transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.repositories.TransactionRepository;

@Service
public class TransactionCreateCommand implements VoidCommandInterface {
    @Override
    public void execute() { //when execute is invoked, a transaction is created as described below
        long transactionTotal = 0L; //variable to keep track of transaction total cost
        final List<TransactionEntryEntity> transactionEntries = new ArrayList<>();

        //TODO: products need to be added to transaction when clicked, need to be able to adjust quantity
        for (ProductEntity productEntity : this.productRepository.findAll())
        {
            int purchasedQuantity = 1; //right now hard coded for one
            transactionTotal += (productEntity.getPrice() * purchasedQuantity); //grabs price of entry and multiplies by quantity
            transactionEntries.add(
                (new TransactionEntryEntity())
                    .setPrice(productEntity.getPrice())
                    .setProductId(productEntity.getId())
                    .setQuantity(purchasedQuantity)); //adds entry to the entries ArrayList *NOTE: Id is set below
            
        }
        this.createTransaction(transactionEntries, transactionTotal); //persists entry to database
    }


//Helper methods
@Transactional
//this method creates a transaction to be persisted to the TransactionEntity table
//out of the entries and total it is given
private void createTransaction(
    final List<TransactionEntryEntity> transactionEntryEntities,
    final long transactionTotal
) {
        //persist to entity using save method
        final TransactionEntity transactionEntity = this.transactionRepository.save(
            (new TransactionEntity(this.employeeId, transactionTotal, 1)));

        //loop through entries and set the same transaction Id for each
        for(TransactionEntryEntity transactionEntryEntity : transactionEntryEntities) {
            transactionEntryEntity.setTransactionId(transactionEntity.getId());
            //persist individual entries to the TransactoionEntryEntity table
            this.transactionEntryRepository.save(transactionEntryEntity);
        }
}

// Properties
private UUID employeeId;
public UUID getEmployeeId() {
    return this.employeeId;
}
public TransactionCreateCommand setEmployeeId(final UUID employeeId) {
    this.employeeId = employeeId;
    return this;
}

//Repositories
@Autowired
ProductRepository productRepository;

@Autowired
private TransactionRepository transactionRepository;

@Autowired
private TransactionEntryRepository transactionEntryRepository;
}