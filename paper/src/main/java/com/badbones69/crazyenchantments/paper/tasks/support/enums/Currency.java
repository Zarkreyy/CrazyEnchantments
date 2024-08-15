package com.badbones69.crazyenchantments.paper.tasks.support.enums;

public enum Currency {
    
    VAULT("vault"),
    XP_LEVEL("xp_level"),
    XP_TOTAL("xp_total");
    
    private final String name;
    
    Currency(final String name) {
        this.name = name;
    }
    
    /**
     * Checks if it is a compatible currency.
     *
     * @param currency The currency name you are checking
     * @return True if it is supported and false if not
     */
    public static boolean isCurrency(String currency) {
        for (Currency value : Currency.values()) {
            if (currency.toLowerCase().equalsIgnoreCase(value.getName())) return true;
        }

        return false;
    }
    
    /**
     * Get a currency enum.
     *
     * @param currency The currency you want
     * @return The currency enum
     */
    public static Currency getCurrency(String currency) {
        for (Currency value : Currency.values()) {
            if (currency.toLowerCase().equalsIgnoreCase(value.getName())) return value;
        }

        return Currency.XP_LEVEL;
    }
    
    /**
     * Get the name of the currency.
     *
     * @return The name of the currency
     */
    public String getName() {
        return this.name;
    }
}