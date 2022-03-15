package com.danielfinal.finalproject.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity (name = "Mail_box")
@Table (
        name = "tbl_mail_box"
)
public class MailBox {

    @Id
    @SequenceGenerator(
            name = "mail_box_sequence",
            sequenceName = "mail_box_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mail_box_sequence"
    )
    @Column(
            name = "mail_box_id",
            updatable = false
    )
    private long id;

//    TODO: verify that the bidirectional relation is done
    @OneToOne(
            mappedBy = "mailBox"
    )
    @JoinColumn(name = "user_id")
    private User user;

}
