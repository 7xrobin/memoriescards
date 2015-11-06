package com.example.vntcaro.memocard.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by vntcaro on 19/10/2015.
 */
@Table(name="Card")
public class Card extends Model{

    @Column(name="Deck", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Deck deck;
    @Column(name="Front")
    public String front;
    @Column(name="Back")
    public String back;
    @Column(name="Hint")
    public String hint;
    @Column(name="DataCreate")
    public Date dateCreate;
    @Column(name="DataUpdade")
    public Date dateUpdate;

    public Card(){super();}

    public Card(Deck deck, String front, String back, String hint){
        super();
        this.deck= deck;
        this.front= front;
        this.back= back;
        this.hint= hint;
        Date date = new Date(System.currentTimeMillis());
        this.dateCreate=date;
        this.dateUpdate=date;
    }

    /***
     * Get all cards of a deck in data base
     * */
    public static List<Card> getAll(long deckId){
        return new Select()
                .from(Card.class)
                .where("Deck=?",deckId)
                .execute();
    }
}
