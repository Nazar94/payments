package command;

import exception.IncorrectCurrencyException;
import exception.IncorrectFormatException;
import model.Currency;
import model.PaymentEntry;
import utils.Utils;

import java.util.List;


public class TotalCommand extends Command {
    public TotalCommand(List<PaymentEntry> history) {
        super(history);
    }

    @Override
    public void execute(String[] params) throws IncorrectFormatException, IncorrectCurrencyException {
        if(params.length != 2) {
            throw new IncorrectFormatException();
        }
        Currency toCurrency = Currency.valueOf(params[1]);
        double sum = history.stream().mapToDouble(x -> getPrefCosts(x, toCurrency)).sum();
        System.out.println(sum + " " + toCurrency.name());
    }

    private Double getPrefCosts(PaymentEntry paymentEntry, Currency toCurrency) {
        if(paymentEntry.getCurrency() == toCurrency) {
            return paymentEntry.getCost();
        }
        return Utils.currencyConverter(paymentEntry.getCost(), paymentEntry.getCurrency(), toCurrency);
    }
}
