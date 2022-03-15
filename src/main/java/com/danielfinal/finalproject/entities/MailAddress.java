package com.danielfinal.finalproject.entities;

import lombok.*;

import javax.persistence.*;

@Entity (name = "Mail_Address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "tbl_mail_address"
)
public class MailAddress {

    @Id
    @SequenceGenerator(
            name = "mail_address_sequence",
            sequenceName = "mail_address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mail_address_sequence"
    )
    @Column(
            name = "mail_address_id",
            updatable = false
    )
    private Long id;


    @OneToOne(
            mappedBy = "mailAddress"
    )
    @JoinColumn(name = "user_id")
    private User user;

//    private String firstName = user.getFirstName();
//    private String lastName = user.getLastName();
//    private String username = user.getUsername();

}
