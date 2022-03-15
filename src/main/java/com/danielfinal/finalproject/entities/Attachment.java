package com.danielfinal.finalproject.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity(name = "Attachment")
@Table(
        name = "tbl_attachment"
)
public class Attachment {

    @Id
    @GeneratedValue(
            generator = "uuid"
    )
    @GenericGenerator(
            name = "uuid",
            strategy = "uuid2"
    )
    @Column(
            name = "attachment_id",
            updatable = false
    )
    private String id;

    private String name;
    private String type;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

}