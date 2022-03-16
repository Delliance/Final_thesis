package com.danielfinal.finalproject.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity (name = "Message")
@Table(
        name = "tbl_message"
)
public class Message {

    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_sequence"
    )
    @Column(
            name = "message_id",
            updatable = false
    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private MailBox receiver_main;

    @ManyToOne
    @JoinColumn(name = "cc_id")
    private MailBox receiver_cc;

    @ManyToOne
    @JoinColumn(name = "bcc_id")
    private MailBox receiver_bcc;




}
