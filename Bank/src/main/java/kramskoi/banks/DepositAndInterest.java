/**
 * A record class representing a deposit and its interest rate.
 *
 * @author <a href="mailto:s.kuznetsov@itmo.ru">Sergey Kuznetsov</a>
 */
package ru.kramskoi.banks;

import java.math.BigDecimal;
import java.util.Comparator;

public record DepositAndInterest(BigDecimal deposit, float interest) {
    /**
     * A comparator that sorts deposits in ascending order by their deposit amount.
     */
    public static final Comparator<DepositAndInterest> ByDepositAscending = Comparator.comparing(d -> d.deposit);

    /**
     * A comparator that sorts deposits in descending order by their deposit amount.
     */
    public static final Comparator<DepositAndInterest> ByDepositDescending = (d1, d2) -> d2.deposit.compareTo(d1.deposit);

    /**
     * A comparator that sorts deposits in ascending order by their interest rate.
     */
    public static final Comparator<DepositAndInterest> ByInterestAscending = Comparator.comparing(d -> d.interest);

    /**
     * A comparator that sorts deposits in descending order by their interest rate.
     */
    public static final Comparator<DepositAndInterest> ByInterestDescending = (d1, d2) -> Float.compare(d2.interest, d1.interest);
}