public class Card {
    private String cardName;
    private String cardType;
    private String cardDamage;
    private String cardSet;
    private String cardVariant;

    /*
    CARD
    This class represents an individual card
    in the databse.

    Here, I could have made multiple overloaded
    constructors, which would allow me to consider
    different scenarios, for example if the user
    only knew the name, damage, type and set,
    but not the variant.
    However, I opted to make only one for
    simplicity's sake and addressed possible
    complications elsewhere.

    Commit 2: added the toData function for
    ease of converting card into a readable/writeable
    format
     */
    public Card(String cN, String cT, String cD,
                String cS, String cV) {
        cardName = cN;
        cardType = cT;
        cardDamage = cD;
        cardSet = cS;
        cardVariant = cV;
    }

    //getters
    public String getCardName() {
        return cardName;
    }
    public String getCardType() {
        return cardType;
    }
    public String getCardDamage() {
        return cardDamage;
    }
    public String getCardSet() {
        return cardSet;
    }
    public String getCardVariant() {
        return cardVariant;
    }
    //getters

    //explanation concerning toString inclusion can be
    //found in main
    @Override
    public String toString() {
        return "Name: " + cardName + ", Type: " + cardType + ", Damage: "
                + cardDamage + ", Variant: " + cardVariant + ", Set: "
                + cardSet;
    }

    public String toData() {
        return cardName + "," + cardType + "," + cardDamage + ","
                + cardSet + "," + cardVariant;
    }





}
