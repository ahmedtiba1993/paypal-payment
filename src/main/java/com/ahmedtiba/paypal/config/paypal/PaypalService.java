package com.ahmedtiba.paypal.config.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaypalService {

    private final APIContext apiContext;

    // Method to create a PayPal payment
    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException {

        // Create an Amount object
        Amount amount = new Amount();
        amount.setCurrency(currency);
        // Format the total amount
        amount.setTotal(String.format(Locale.forLanguageTag(currency),"%.2f",total)); //9.99$ - 9,99€

        // Create a Transaction object
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        // Create a list of transactions
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // Create a Payer object
        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        // Create a Payment object
        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        // Set redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    // Method to execute a PayPal payment
    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException {

        // Create a Payment object with the provided paymentId
        Payment payment = new Payment();
        payment.setId(paymentId);

        // Create a PaymentExecution object with the provided payerId
        PaymentExecution execution = new PaymentExecution();
        execution.setPayerId(payerId);

        return payment.execute(apiContext, execution);
    }

}
