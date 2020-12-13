package com.cheef.collections;

import java.util.*;

public class PhoneBook {
    private final Map<String, List<String>> contacts;

    public Map<String, List<String>> getContacts() {
        return contacts;
    }

    public PhoneBook() {
        contacts = new HashMap<>();
    }

    public void add( String fio, String phone ) {
        List<String> phones = this.get( fio );
        if ( phones == null ) {
            phones = new ArrayList<>();
        }
        phones.add( phone );
        contacts.put( fio, phones );
    }

    public List<String> get( String fio ) {
        return contacts.get( fio );
    }
}
