package ru.itmo.banks;

import java.util.Comparator;

public record DepositAndInterest(int deposit, float interest) {
    public static final Comparator<DepositAndInterest> ByDepositAscending = (d1, d2) -> Integer.compare(d1.deposit, d2.deposit);

    public static final Comparator<DepositAndInterest> ByDepositDescending = (d1, d2) -> Integer.compare(d2.deposit, d1.deposit);

    public static final Comparator<DepositAndInterest> ByInterestAscending = (d1, d2) -> Float.compare(d1.interest, d2.interest);

    public static final Comparator<DepositAndInterest> ByInterestDescending = (d1, d2) -> Float.compare(d2.interest, d1.interest);

}
