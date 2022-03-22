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
@Entity(name = "Message")
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

    @Column(
            name = "sender",
            nullable = false
    )
    private String sender;

    @Column(
            name = "subject"
    )
    private String subject = "No subject";

    @Column(
            name = "body"
    )
    private String body = "empty message";

    @Column(
            name = "primary_receptor",
            nullable = false
    )
    private String primaryReceptor;

    @Column(
            name = "carbon_copy"
    )
    private String carbonCopy;

    @Column(
            name = "blind_carbon_copy"
    )
    private String blindCarbonCopy;
//
//    @OneToMany(
//            cascade = CascadeType.ALL
//    )
//    private List<Attachment> attachment;

//    @OneToMany
//    private List<Tag> tag;

//    TODO: this is just for the sender, make multiple for all the receivers as well

    @ManyToOne
    @JoinColumn(name = "mailbox_id")
    private Mailbox mailbox;

}
