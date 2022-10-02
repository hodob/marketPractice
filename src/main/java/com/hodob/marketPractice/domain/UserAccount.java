package com.hodob.marketPractice.domain;


import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserAccount extends User {

    private Account account;

    public UserAccount(
            Account account
    ) {
        super(
                account.getUsername(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole()))
        );

        // Domain account 접근할 수 있도록 추가
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
