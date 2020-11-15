package com.cheef.generics;

import java.util.ArrayList;

public class Box<E extends Fruit> {
    protected ArrayList<E> items;

    public void setItems( ArrayList<E> items ) {
        this.items = items;
    }

    public Box() {
        this.items = new ArrayList<>();
    }

    public Box( ArrayList<E> items ) {
        this.items = items;
    }

    public void addItem( E item ) {
        if ( items == null ) {
          items = new ArrayList<>();
        }
        items.add( item );
    }

    public boolean removeItem( E item ) {
       return items.remove( item );
    }

    public float getWeight() {
        float totalWeight = 0;

        if ( items == null ) {
            return 0;
        }

        for ( E el: items ) {
            totalWeight += el.getWeight();
        }
        return totalWeight;
    }

    public <T extends Fruit> boolean compare( Box<T> list ) {
        return this.getWeight() == list.getWeight();
    }

    public void pull( Box<E> box ) {
        box.setItems( this.items );
        this.items = null;
    }
}
