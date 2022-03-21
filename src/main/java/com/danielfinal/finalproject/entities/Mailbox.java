package com.danielfinal.finalproject.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
public class Mailbox {

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

    @OneToOne(
            mappedBy = "mailBox"
    )
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany (
//            fetch = FetchType.EAGER
//    )
//    private List<Message> message;

}
