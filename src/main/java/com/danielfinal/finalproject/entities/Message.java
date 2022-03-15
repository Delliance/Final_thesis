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
    @JoinColumn(name = "mail_box_id")
    private MailBox mailBox;




}
