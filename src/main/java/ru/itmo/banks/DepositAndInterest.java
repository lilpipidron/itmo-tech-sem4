package ru.itmo.banks;

import java.math.BigDecimal;
import java.util.Comparator;

public record DepositAndInterest(BigDecimal deposit, float interest) {
    public static final Comparator<DepositAndInterest> ByDepositAscending = Comparator.comparing(d -> d.deposit);

    public static final Comparator<DepositAndInterest> ByDepositDescending = (d1, d2) -> d2.deposit.compareTo(d1.deposit);

    public static final Comparator<DepositAndInterest> ByInterestAscending = Comparator.comparing(d -> d.interest);

    public static final Comparator<DepositAndInterest> ByInterestDescending = (d1, d2) -> Float.compare(d2.interest, d1.interest);
}