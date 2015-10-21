package com.example.vntcaro.memocard.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by vntcaro on 06/10/2015.
 *
 * Model class of Deck for use in all aplication powered by ActiveAndroid library
 */

@Table(name="Deck")
public class Deck extends Model {

    /**
     * Id feild it's handle by Active Library
     * **/
    @Column(name="Name")
    public String name;
    @Column(name="Description")
    public String description;
    @Column(name="DataCreate")
    public Date  dateCreate;
    @Column(name="DataUpdade")
    public Date dateUpdate;

    public Deck(){
        super();
    }

    public Deck(String deckName,String description){
        super();
        this.name=deckName;
        this.description= description;
        Date date = new Date(System.currentTimeMillis());
        this.dateCreate=date;
        this.dateUpdate=date;
    }
    /***Get an specific Deck by a DeckId*/
    public static Deck getDeck(long deckId){
        return new Select()
                .from(Deck.class)
                .where("_id=?", deckId)
                .executeSingle();
    }
    /***
     * Get all instances of deck in data base
     * */
    public static List<Deck> getAll(){
        return new Select()
                .from(Deck.class)
                .execute();
    }
}
